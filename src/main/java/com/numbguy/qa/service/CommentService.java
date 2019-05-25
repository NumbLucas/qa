package com.numbguy.qa.service;

import com.numbguy.qa.dao.CommentDAO;
import com.numbguy.qa.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }
    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.getCommentsByEntity(entityId, entityType);
    }

    public int getCommentsCount(int entityId, int entityType) {
        return  commentDAO.getCommentCount(entityId, entityType);
    }

    public boolean deleteCOmment(int commentId) {
        return commentDAO.updateStatus(commentId, 1)>0;
    }

    public Comment getCommentById(int id) {
        return commentDAO.getCommentById(id);
    }
}
