package com.numbguy.qa.dao;

import com.numbguy.qa.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String SELECT_FIELD = " id, name, password, salt, head_url ";
    String INSERT_FIELD = " name, password, salt, head_url ";

    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELD, ") VALUES(#{name}, #{password}, #{salt}, #{headUrl})" })
    int addUser(User user);

    @Select({"SELECT ", SELECT_FIELD, " FROM ", TABLE_NAME, " WHERE id =#{id}"})
    User getUserById(int id);

    @Select({"SELECT ", SELECT_FIELD, " FROM ", TABLE_NAME, " WHERE name =#{name}"})
    User getUserByName(String name);

    @Delete({"delete FROM ", TABLE_NAME, "where id=#{id}"})
    void deleteUser(int id);
}
