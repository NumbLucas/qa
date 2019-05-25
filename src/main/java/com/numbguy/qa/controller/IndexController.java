package com.numbguy.qa.controller;

import com.numbguy.qa.model.Question;
import com.numbguy.qa.model.ViewObject;
import com.numbguy.qa.service.QuestionService;
import com.numbguy.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLastedQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        List<ViewObject> list = getQuestions(0, 0, 10);
        System.out.println("count: " + list.size());
        model.addAttribute("vos", list);
        //model.addAttribute("vos", list);
        return "index";
    }

    @RequestMapping(path = {"/test"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String test(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        List<ViewObject> list = getQuestions(1, 0, 10);

        return "test";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }


}
