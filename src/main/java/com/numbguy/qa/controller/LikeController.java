package com.numbguy.qa.controller;

import com.numbguy.qa.QAUtil.JSON;
import com.numbguy.qa.async.EventModel;
import com.numbguy.qa.async.EventProducer;
import com.numbguy.qa.async.EventType;
import com.numbguy.qa.model.Comment;
import com.numbguy.qa.model.EntityType;
import com.numbguy.qa.model.HostHolder;
import com.numbguy.qa.service.CommentService;
import com.numbguy.qa.service.LikeService;
import com.numbguy.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if(hostHolder.getUser() == null) {
            return JSON.getJSONString(999);
        }

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, commentId);
        Comment comment = commentService.getCommentById(commentId);
        EventModel eventModel = new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_COMMENT).setEntityId(commentId).
                setExt("questionId", String.valueOf(comment.getEntityId()))
                .setEntityOwner(comment.getUserId());
        eventProducer.fireEvent(eventModel);
        return JSON.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId) {
        if(hostHolder.getUser() == null) {
            return JSON.getJSONString(999);
        }

        long LikeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, commentId);
        return JSON.getJSONString(0, String.valueOf(LikeCount));
    }
}
