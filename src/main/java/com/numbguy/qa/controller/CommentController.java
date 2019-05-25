package com.numbguy.qa.controller;

import com.numbguy.qa.QAUtil.OtherUtils;
import com.numbguy.qa.dao.CommentDAO;
import com.numbguy.qa.model.Comment;
import com.numbguy.qa.model.EntityType;
import com.numbguy.qa.model.HostHolder;
import com.numbguy.qa.model.Question;
import com.numbguy.qa.service.CommentService;
import com.numbguy.qa.service.QuestionService;
import com.numbguy.qa.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(sensitiveService.filter(content));
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            comment.setEntityId(questionId);
            comment.setStatus(0);
            if(hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                comment.setUserId(OtherUtils.ANONYMOUS_USERID);
            }
            commentService.addComment(comment);

            int count = commentService.getCommentsCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
        }catch (Exception e){
            System.out.println("添加评论失败");
        }

        return "redirect:/question/" + questionId;
    }
}
