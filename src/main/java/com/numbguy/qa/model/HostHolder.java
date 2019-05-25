package com.numbguy.qa.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    public static ThreadLocal<User> hostHolder= new ThreadLocal<User>();
    public User getUser() { return hostHolder.get(); }
    public void setUser(User user) { hostHolder.set(user); }
    public  void clear() { hostHolder.remove(); }
}
