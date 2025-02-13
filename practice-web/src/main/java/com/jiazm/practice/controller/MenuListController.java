package com.jiazm.practice.controller;

import com.jiazm.practice.req.menulist.DeleteReq;
import com.jiazm.practice.req.menulist.ListReq;
import com.jiazm.practice.req.menulist.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.service.MenuListService;
import com.jiazm.practice.vo.DictVo;
import com.jiazm.practice.vo.menulist.MenuListListVo;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * menuList Controller
 *
 * @author jiazm3
 * @date 2024-11-15 16:13:50
 */
@RestController
@RequestMapping("/menuList")
public class MenuListController {

    @Resource
    private MenuListService menuListService;
    @Resource
    private OllamaChatModel ollamaChatModel;

    /**
     * 更新数据
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/modify")
    public GeneralResponse<String> modify(@RequestBody @Valid ModifyReq req) {
        return menuListService.modify(req);
    }

    /**
     * 列表数据
     *
     * @param req
     * @return
     */
    @PostMapping("/list")
    public GeneralResponse<MenuListListVo> list(@RequestBody @Valid ListReq req) {
        return menuListService.list(req);
    }

    /**
     * menuLevel 列表
     *
     * @return
     */
    @GetMapping("/menuLevel")
    public GeneralResponse<List<DictVo>> menuLevelList() {
        return menuListService.menuLevel();
    }

    /**
     * 删除数据
     *
     * @param req
     * @return
     */
    @PostMapping("/del")
    public GeneralResponse<String> del(@RequestBody @Valid DeleteReq req) {
        return menuListService.del(req);
    }

    /**
     * @return
     */
    @GetMapping("/ollama/deepseek")
    public GeneralResponse<String> deepSeek() {
        String message = "牛顿是谁？";
        Prompt prompt = new Prompt(new UserMessage(message));
        ChatResponse chatResponse = ollamaChatModel.call(prompt);
        String content = chatResponse.getResult().getOutput().getContent();
        System.out.println(content);
        return GeneralResponse.success(content);
    }
}
