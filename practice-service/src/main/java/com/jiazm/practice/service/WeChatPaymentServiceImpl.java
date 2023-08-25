package com.jiazm.practice.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiazm.practice.config.WxV3PayConfig;
import com.jiazm.practice.constants.WxPayConstants;
import com.jiazm.practice.unit.WXPaySignatureCertificateUtil;
import com.jiazm.practice.unit.WxPayUtil;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiazhongmin
 * @Date 2023/8/25 11:05
 **/
@Slf4j
@Service
public class WeChatPaymentServiceImpl implements WechatPaymentService {



    /**
     * V3微信支付统一下单
     *
     * @return
     *
     */
    @Override
    public Map<String, Object> weChatDoUnifiedOrder() {
        Map<String, Object>  map= new HashMap<>();
        //支付总金额
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = totalPrice.add(BigDecimal.valueOf(600));
        //转换金额保留两位小数点
        Integer money=new BigDecimal(String.valueOf(totalPrice)).movePointRight(2).intValue();
        try {
            //验证证书
            CloseableHttpClient httpClient = WXPaySignatureCertificateUtil.checkSign();
            //app下单
            HttpPost httpPost = new HttpPost(WxPayConstants.DOMAIN_API+WxPayConstants.PAY_TRANSACTIONS_APP);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid", "商户id")
                    .put("appid", "APPID")
                    .put("description","描述")
                    .put("notify_url", WxPayConstants.WECHAT_PAY_NOTIFY_URL)//回调
                    .put("out_trade_no", "订单号");
            rootNode.putObject("amount")
                    .put("total","总金额");
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            //完成签名并执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //获取返回状态
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject object = JSONObject.parseObject(result);
                //获取预付单
                String prepayId = object.getString("prepay_id");
                //生成签名
                Long timestamp = System.currentTimeMillis() / 1000;
                //随机字符串32位
                String nonceStr = WxPayUtil.generateNonceStr(32);
                //生成带签名支付信息
                String paySign = WXPaySignatureCertificateUtil.appPaySign(String.valueOf(timestamp), nonceStr, prepayId);
                Map<String, String> param = new HashMap<>();
                param.put("appid", WxV3PayConfig.APP_ID);
                param.put("partnerid", WxV3PayConfig.Mch_ID);
                param.put("prepayid", prepayId);
                param.put("package", "Sign=WXPay");
                param.put("noncestr", nonceStr);
                param.put("timestamp", String.valueOf(timestamp));
                param.put("sign", paySign);
                map.put("code",200);
                map.put("message", "下单成功");
                map.put("data", param);
                return map;
            }
            map.put("code",200);
            map.put("message", "下单失败");
            map.put("data", response);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 微信支付回调通知
     * @return
     */
    @Override
    public Map<String, Object> weChatNotificationHandler(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        try {
            BufferedReader br = request.getReader();
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine())!=null) {
                sb.append(str);
            }
            // 构建request，传入必要参数
            NotificationRequest requests = new NotificationRequest.Builder()
                    .withSerialNumber(request.getHeader("Wechatpay-Serial"))
                    .withNonce(request.getHeader("Wechatpay-Nonce"))
                    .withTimestamp(request.getHeader("Wechatpay-Timestamp"))
                    .withSignature(request.getHeader("Wechatpay-Signature"))
                    .withBody(String.valueOf(sb))
                    .build();
            //验签
            NotificationHandler handler = new NotificationHandler(WXPaySignatureCertificateUtil.getVerifier(WxV3PayConfig.mchSerialNo), WxV3PayConfig.apiV3Key.getBytes(StandardCharsets.UTF_8));
            //解析请求体
            Notification notification = handler.parse(requests);
            String decryptData = notification.getDecryptData();
            //解析
            JSONObject jsonObject = JSONObject.parseObject(decryptData);
            //支付状态交易状态，枚举值： SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（付款码支付）
            // USERPAYING：用户支付中（付款码支付） PAYERROR：支付失败(其他原因，如银行返回失败)
            String trade_state = String.valueOf(jsonObject.get("trade_state"));
            if (trade_state.equals("SUCCESS")) {
                //订单号
                String orderNumber = String.valueOf(jsonObject.get("out_trade_no"));
                //微信支付微信生成的订单号
                String transactionId = String.valueOf(jsonObject.get("transaction_id"));
                //省略查询订单
                //此处处理业务
                map.put("code","SUCCESS");
                map.put("message","成功");
                //消息推送成功
                return map;
            }
            map.put("code","RESOURCE_NOT_EXISTS");
            map.put("message", "订单不存在");
            return map;
        }catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code","FAIL");
        map.put("message", "失败");
        return map;
    }
    /**
     * 关闭订单
     * @param outTradeNo 订单号
     * @return
     */
    @Override
    public Map<String, Object> closeOrder(String outTradeNo) {
        Map<String,Object> map = new HashMap<>();
        try {
            //验证证书
            CloseableHttpClient httpClient = WXPaySignatureCertificateUtil.checkSign();
            //关闭订单
            String url = String.format(WxPayConstants.DOMAIN_API+WxPayConstants.PAY_TRANSACTIONS_OUT_TRADE_NO, outTradeNo);
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //2.添加商户id
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("mchid", WxV3PayConfig.Mch_ID);
            objectMapper.writeValue(bos, rootNode);
            //3.调起微信关单接口
            httpPost.setEntity(new StringEntity(bos.toString(StandardCharsets.UTF_8), "UTF-8"));
            //完成签名并执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode() == 204);
            //无数据（Http状态码为204） 微信返回结果无数据 状态码为204 成功
            if (response.getStatusLine().getStatusCode() == 204) {

                //code 退款码请前往微信支付文档查询
                map.put("code",200);
                map.put("message", "关闭订单成功！");
                return map;
            }
        } catch (Exception e) {
            log.error("关单失败:" + outTradeNo + e);
        }
        return null;
    }

    /**
     * 微信退款
     * @param outTradeNo 订单号
     * @return
     */
    @Override
    public Map<String, Object> weChatRefunds(String outTradeNo) {
        Map<String,Object> map = new HashMap<>();
        //退款总金额
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = totalPrice.add(BigDecimal.valueOf(600));
        //转换金额
        Integer money=new BigDecimal(String.valueOf(totalPrice)).movePointRight(2).intValue();

        try {
            //验证证书
            CloseableHttpClient httpClient = WXPaySignatureCertificateUtil.checkSign();
            //申请退款接口
            HttpPost httpPost = new HttpPost(WxPayConstants.DOMAIN_API+WxPayConstants.REFUND_DOMESTIC_REFUNDS);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            //微信支付订单号
            rootNode.put("transaction_id", "微信支付订单号")
                    //退款订单号
                    .put("out_refund_no","生成退款订单号")
                    .put("notify_url","退款回调");
            //退款金额
            rootNode.putObject("amount")
                    .put("refund", "100.00")
                    //原订单金额
                    .put("total", "100.00")
                    .put("currency","CNY");
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString(StandardCharsets.UTF_8), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //退款成功返回消息
            String bodyAsString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
            if (jsonObject.get("status").equals("SUCCESS") || jsonObject.get("status").equals("PROCESSING")) {
                //code返回
                map.put("code",200);
                map.put("message", "退款成功");
                return map;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code",500);
        map.put("message", "申请退款失败！");
        return map;
    }

    /**
     * 申请退款回调
     * @param request
     * @return
     */
    @Override
    public Map<String,Object> weChatPayRefundsNotify(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        try {
            BufferedReader br = request.getReader();
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine())!=null) {
                sb.append(str);
            }
            // 构建request，传入必要参数
            NotificationRequest requests = new NotificationRequest.Builder()
                    .withSerialNumber(request.getHeader("Wechatpay-Serial"))
                    .withNonce(request.getHeader("Wechatpay-Nonce"))
                    .withTimestamp(request.getHeader("Wechatpay-Timestamp"))
                    .withSignature(request.getHeader("Wechatpay-Signature"))
                    .withBody(String.valueOf(sb))
                    .build();
            //验签
            NotificationHandler handler = new NotificationHandler(WXPaySignatureCertificateUtil.getVerifier(WxV3PayConfig.mchSerialNo), WxV3PayConfig.apiV3Key.getBytes(StandardCharsets.UTF_8));
            //解析请求体
            Notification notification = handler.parse(requests);
            String decryptData = notification.getDecryptData();
            //解析
            JSONObject jsonObject = JSONObject.parseObject(decryptData);
            String refund_status = String.valueOf(jsonObject.get("refund_status"));
            if (refund_status.equals("SUCCESS")) {
                //订单号
                String orderNumber = String.valueOf(jsonObject.get("out_trade_no"));
                //微信支付订单号
                String transactionId = String.valueOf(jsonObject.get("transaction_id"));

                //这里是处理业务逻辑


                //code 退款码请前往微信支付文档查询
                map.put("code","RESOURCE_NOT_EXISTS");
                map.put("message", "订单不存在");
                return map;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        map.put("code","USER_ACCOUNT_ABNORMAL");
        map.put("message", "退款请求失败");
        return map;
    }

}
