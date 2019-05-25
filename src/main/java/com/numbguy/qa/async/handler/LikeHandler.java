package com.numbguy.qa.async.handler;

import com.numbguy.qa.async.EventHandler;
import com.numbguy.qa.async.EventModel;
import com.numbguy.qa.async.EventType;
import com.numbguy.qa.model.Message;
import com.numbguy.qa.model.User;
import com.numbguy.qa.service.MessageService;
import com.numbguy.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(0);
        message.setToId(eventModel.getEntityOwner());
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());
        message.setContent("用户 " + user.getName() + "赞了你的评论");
        messageService.addMessgae(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE, EventType.COMMENT);
    }
}
