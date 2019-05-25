package com.numbguy.qa.service;

import com.numbguy.qa.QAUtil.MD5;
import com.numbguy.qa.dao.TicketDAO;
import com.numbguy.qa.dao.UserDAO;
import com.numbguy.qa.model.LogInTicket;
import com.numbguy.qa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TicketDAO ticketDAO;

    public int addUser(User user) {
        return userDAO.addUser(user);
    }

    public User getUser(int id) {
        return  userDAO.getUserById(id);
    }
    public User getUserByName(String name) {return  userDAO.getUserByName(name); }

    public Map<String, String> register(String userName, String password) {
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isEmpty(userName)) {
            map.put("msg", "用户名为空");
            return  map;
        }
        if(StringUtils.isEmpty(password)) {
            map.put("msg", "密码为空");
            return map;
        }
        User user = userDAO.getUserByName(userName);
        if(user != null) {
            map.put("msg", "用户已被注册");
            return  map;
        }
        user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://numbguy/head/%d", new Random().nextInt(1000)));
        user.setPassword(MD5.MD5Encode(password+user.getSalt(), "utf-8"));
        userDAO.addUser(user);

        String ticket = addLogInTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, String> login(String userName, String password) {
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isEmpty(userName)) {
            map.put("msg", "用户名为空");
            return  map;
        }
        if(StringUtils.isEmpty(password)) {
            map.put("msg", "密码为空");
            return map;
        }
        User user = userDAO.getUserByName(userName);
        if(user == null) {
            map.put("msg", "用户不存在");
            return  map;
        }
        if(!MD5.MD5Encode(password + user.getSalt(), "utf-8").equals(user.getPassword())) {
            map.put("msg", "密码错误");
        }
        String ticket = addLogInTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    private String addLogInTicket(int userID){
        LogInTicket ticket = new LogInTicket();
        ticket.setUserId(userID);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-",""));
        ticketDAO.addTicket(ticket);
        return  ticket.getTicket();
    }

    public  void logout(String ticket) {
        ticketDAO.updateStatus(ticket, 1);
    }
}
