package com.numbguy.qa.service;

import com.numbguy.qa.dao.MessageDAO;
import com.numbguy.qa.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private  SensitiveService sensitiveService;

    public int addMessgae(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        int rst = messageDAO.addMessage(message);
        System.out.println(rst);
        return rst;
    }

    public List<Message> getConversationDeatil(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList( int userId, int offset, int limit) {
        return  messageDAO.getConversationList(userId, offset, limit);
    }

    public  int getUnreadCount(String conversationId, int userId) {
        return messageDAO.getUnreadCount(conversationId, userId);
    }
}
