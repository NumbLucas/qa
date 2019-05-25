package com.numbguy.qa.dao;

import com.numbguy.qa.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELD = " user_id,  content, created_date, entity_id, entity_type, status";
    String SELECT_FIELD = " id, " + INSERT_FIELD;
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELD,
            ") values(#{userId},#{content}, #{createdDate}, #{entityId}, #{entityType}, #{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELD, "from ", TABLE_NAME, "where entity_type=#{entityType} and entity_id=#{entityId} order by created_date desc"})
    List<Comment> getCommentsByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(*) from ", TABLE_NAME, "where entity_type=#{entityType} and entity_id=#{entityId}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select ", SELECT_FIELD, " from ", TABLE_NAME, "where id=#{id}"})
    Comment getCommentById(@Param("id") int id);

    @Delete({"update comment set status=#{status}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
