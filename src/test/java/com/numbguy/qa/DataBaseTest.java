package com.numbguy.qa;

import com.numbguy.qa.dao.QuestionDAO;
import com.numbguy.qa.model.Message;
import com.numbguy.qa.model.Question;
import com.numbguy.qa.model.User;
import com.numbguy.qa.service.MessageService;
import com.numbguy.qa.service.QuestionService;
import com.numbguy.qa.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBaseTest {
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionDAO questionDAO;

	@Autowired
	private MessageService messageService;
	@Test
	public void contextLoads() {
		System.out.println("test");
//		for(int i = 0;i < 5; i++)
//		{
//			User user = new User();
//			user.setName(String.format("User%d", i));
//			user.setPassword(String.format("Password%d", i));
//			user.setSalt("");
//			user.setHeadUrl("");
//			userService.addUser(user);
//		}
		User user = userService.getUser(1);
		System.out.println(user);

//		for(int i = 0;i < 5;i++) {
//			Question q = new Question();
//			q.setUserId(i);
//			q.setCommentCount(i + 2);
//			q.setContent(String.format("Cotent%d", i));
//			q.setCreatedDate(new Date());
//			q.setTitle(String.format("TITLE%d", i));
//			questionDAO.addQuestion(q);
//		}
//		Question q = new Question();
//		q.setUserId(1);
//		q.setCommentCount(5);
//		q.setContent("测试多个questions");
//		q.setCreatedDate(new Date());
//		q.setTitle("测试标题");
//		questionDAO.addQuestion(q);
		Message message = new Message();
		message.setHasRead(0);
		message.setToId(1);
		message.setFromId(3);
		message.setCreatedDate(new Date());
		message.setContent("Content");
		message.setConversationId("1_3");
		messageService.addMessgae(message);

		List<Question> list = questionDAO.getLastedQuestion(1,0, 1);

		for(Question l:list) {
			System.out.println(l);
		}

	}
}
