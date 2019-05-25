package com.numbguy.qa.controller;

import com.numbguy.qa.QAUtil.JSON;
import com.numbguy.qa.QAUtil.OtherUtils;
import com.numbguy.qa.model.*;
import com.numbguy.qa.service.CommentService;
import com.numbguy.qa.service.LikeService;
import com.numbguy.qa.service.QuestionService;
import com.numbguy.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUser() == null) {
                question.setUserId(OtherUtils.ANONYMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question) > 0) {
                return  JSON.getJSONString(0);
            }
        }catch (Exception e) {

        }
        return JSON.getJSONString(1, "failure");
    }

    @RequestMapping(value = "/question/{qid}",method = {RequestMethod.GET})
    public String detail(@PathVariable("qid") int qid, Model model) {
        Question question = questionService.getQueston(qid);
        model.addAttribute("question", question);
        model.addAttribute("user", userService.getUser(question.getUserId()));
        List<Comment> commetnList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for(Comment comment:commetnList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("comment", comment);
            if(hostHolder.getUser() == null) {
                viewObject.set("liked", 0);
            }else {
                viewObject.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), comment.getEntityType(),comment.getUserId()));
            }
            long likeCount = likeService.getLikeCount(comment.getEntityType(),comment.getId());
            viewObject.set("likeCount",likeCount);
            viewObject.set("user", userService.getUser(comment.getUserId()));
            comments.add(viewObject);
        }
        model.addAttribute("comments", comments);
        return "detail";
    }

}
