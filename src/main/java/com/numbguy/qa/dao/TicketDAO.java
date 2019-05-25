package com.numbguy.qa.dao;

import com.numbguy.qa.model.LogInTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TicketDAO {
    String TABLE_NAME = "ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LogInTicket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, "where ticket=#{ticket}"})
    LogInTicket SelectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket =#{ticket}"})
    int updateStatus(@Param("ticket") String ticket,
                     @Param("status") int status);
}
