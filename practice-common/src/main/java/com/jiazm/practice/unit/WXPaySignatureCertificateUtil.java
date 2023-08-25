package com.jiazm.practice.unit;

import com.jiazm.practice.config.WxV3PayConfig;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jiazhongmin
 * @Date 2023/8/25 10:55
 **/
@Data

public class WXPaySignatureCertificateUtil {

    /**
     * 证书验证
     * 自动更新的签名验证器
     */
    public static CloseableHttpClient checkSign() throws IOException {
        //验签
        CloseableHttpClient httpClient = null;
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(WxV3PayConfig.privateKey.getBytes(StandardCharsets.UTF_8)));
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(WxV3PayConfig.Mch_ID, WxV3PayConfig.mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(WXPaySignatureCertificateUtil.getVerifier(WxV3PayConfig.mchSerialNo)))
                .build();

        return httpClient;
    }




    /**
     * 保存微信平台证书
     */
    private static final ConcurrentHashMap<String, AutoUpdateCertificatesVerifier> verifierMap = new ConcurrentHashMap<>();

    /**
     * 功能描述:获取平台证书，自动更新
     * 注意：这个方法内置了平台证书的获取和返回值解密
     */
   public static AutoUpdateCertificatesVerifier getVerifier(String mchSerialNo) {
        AutoUpdateCertificatesVerifier verifier = null;
        if (verifierMap.isEmpty() || !verifierMap.containsKey(mchSerialNo)) {
            verifierMap.clear();
            try {
                //传入证书
                PrivateKey privateKey = getPrivateKey();
                //刷新
                PrivateKeySigner signer = new PrivateKeySigner(mchSerialNo, privateKey);
                WechatPay2Credentials credentials = new WechatPay2Credentials(WxV3PayConfig.Mch_ID, signer);
                verifier = new AutoUpdateCertificatesVerifier(credentials
                        , WxV3PayConfig.apiV3Key.getBytes(StandardCharsets.UTF_8));
                verifierMap.put(String.valueOf(verifier.getValidCertificate().getSerialNumber()), verifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            verifier = verifierMap.get(mchSerialNo);
        }
        return verifier;
    }



    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String appPaySign(String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey();
        String signatureStr = Stream.of(WxV3PayConfig.APP_ID, timestamp, nonceStr, prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 生成带签名支付信息
     *
     * @param timestamp 时间戳
     * @param nonceStr  随机数
     * @param prepayId  预付单
     * @return 支付信息
     * @throws Exception
     */
    public static String jsApiPaySign(String timestamp, String nonceStr, String prepayId) throws Exception {
        //上传私钥
        PrivateKey privateKey = getPrivateKey();
        String signatureStr = Stream.of(WxV3PayConfig.APP_ID, timestamp, nonceStr, "prepay_id="+prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(sign.sign());
    }


    /**
     * 获取私钥。
     * 证书路径 本地使用如： D:\\微信平台证书工具\\7.9\\apiclient_key.pem
     * 证书路径 线上使用如： /usr/apiclient_key.pem
     * String filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("D:\\微信平台证书工具\\7.9\\apiclient_key.pem")), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }


    /**
     * 功能描述: 验证签名
     * 注意：使用微信支付平台公钥验签
     * Wechatpay-Signature 微信返签名
     * Wechatpay-Serial 微信平台证书序列号
     *
     * @return java.lang.String
     * @author 影子
     */
    @SneakyThrows
    public static boolean verifySign(HttpServletRequest request, String body) {
        boolean verify = false;
        try {
            String wechatPaySignature = request.getHeader("Wechatpay-Signature");
            String wechatPayTimestamp = request.getHeader("Wechatpay-Timestamp");
            String wechatPayNonce = request.getHeader("Wechatpay-Nonce");
            String wechatPaySerial = request.getHeader("Wechatpay-Serial");
            //组装签名串
            String signStr = Stream.of(wechatPayTimestamp, wechatPayNonce, body)
                    .collect(Collectors.joining("\n", "", "\n"));
            //获取平台证书
            AutoUpdateCertificatesVerifier verifier = getVerifier(wechatPaySerial);
            //获取失败 验证失败
            if (verifier != null) {
                Signature signature = Signature.getInstance("SHA256withRSA");
                signature.initVerify(verifier.getValidCertificate());
                //放入签名串
                signature.update(signStr.getBytes(StandardCharsets.UTF_8));
                verify = signature.verify(Base64.getDecoder().decode(wechatPaySignature.getBytes()));
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return verify;
    }
}
