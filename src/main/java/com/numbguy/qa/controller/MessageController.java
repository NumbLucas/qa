package com.numbguy.qa.controller;

import com.numbguy.qa.QAUtil.JSON;
import com.numbguy.qa.model.HostHolder;
import com.numbguy.qa.model.Message;
import com.numbguy.qa.model.User;
import com.numbguy.qa.model.ViewObject;
import com.numbguy.qa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    private HostHolder hostHolder;

    int rst;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                          @RequestParam("content") String content) {
        try {
            if(hostHolder.getUser() ==null) {
                return JSON.getJSONString(999, "未登录");
            }
            User toUser = userService.getUserByName(toName);
            if(toUser == null) {
                return JSON.getJSONString(666, "目标用户不存在");
            }
            User fromUser = hostHolder.getUser();
            Message message = new Message();
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setFromId(fromUser.getId());
            message.setToId(toUser.getId());
            message.setHasRead(0);
            rst= messageService.addMessgae(message);
            System.out.println("rst: "+ rst);
        }catch (Exception e) {
        }
        return  JSON.getJSONString(0);
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if(hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        int localUserId =  hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> conversations = new ArrayList<>();
        for(Message message:conversationList) {
            System.out.println(message);
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            int targetId = message.getFromId();
            vo.set("user", userService.getUser((targetId == localUserId)?message.getToId():targetId ));
            vo.set("unread", messageService.getUnreadCount(message.getConversationId(), localUserId));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try{
            List<Message> messageList = messageService.getConversationDeatil(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for(Message message:messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        }catch (Exception e) {

        }
        return "letterDetail";
    }
}
