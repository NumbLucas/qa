package com.numbguy.qa.dao;

import com.numbguy.qa.model.Comment;
import com.numbguy.qa.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELD = " from_id,  to_id, content, created_date, has_read, conversation_id";
    String INSERT_FIELD_GROUP = "ANY_VALUE(from_id),  ANY_VALUE(to_id), ANY_VALUE(content),ANY_VALUE(created_date), ANY_VALUE(has_read) ,ANY_VALUE(conversation_id)";
    String SELECT_FIELD = " id, " + INSERT_FIELD;
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELD,
            ") values(#{fromId},#{toId}, #{content}, #{createdDate}, #{hasRead}, #{conversationId})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELD, "from ", TABLE_NAME,
            "where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

//    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", TABLE_NAME,
//            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})

//    @Select({"select ", INSERT_FIELD_GROUP, ", count(id) as id from ( select * from ", TABLE_NAME,
//            "where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by ANY_VALUE(created_date) desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) as id from ",TABLE_NAME, " where conversation_id=#{conversationId} and to_id=#{userId} and has_read = 0 " })
    int getUnreadCount(@Param("conversationId") String conversationId,
                       @Param("userId") int userId);
}
