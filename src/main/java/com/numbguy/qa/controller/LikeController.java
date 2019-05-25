package com.numbguy.qa.controller;

import com.numbguy.qa.QAUtil.JSON;
import com.numbguy.qa.model.EntityType;
import com.numbguy.qa.model.HostHolder;
import com.numbguy.qa.service.LikeService;
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

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if(hostHolder.getUser() == null) {
            return JSON.getJSONString(999);
        }

        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, commentId);
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
