package com.numbguy.qa.service;

import com.numbguy.qa.dao.QuestionDAO;
import com.numbguy.qa.dao.UserDAO;
import com.numbguy.qa.model.Question;
import com.numbguy.qa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词汇过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));

        return questionDAO.addQuestion(question)>0?question.getUserId() : 0;
    }

    public Question getQueston(int id) {
        return  questionDAO.getQuestionById(id);
    }

    public List<Question> getLastedQuestion(int userId, int offset, int limit) {
        return  questionDAO.getLastedQuestion(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }


}
