package com.numbguy.qa.dao;

import com.numbguy.qa.model.Question;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELD = " title,  content, created_date, user_id, comment_count";
    String SELECT_FIELD = " id, " + INSERT_FIELD;


    @Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELD, ") VALUES(#{title}," +
            " #{content}, #{createdDate}, #{userId}, #{commentCount})" })
    int addQuestion(Question question);

    @Select({"SELECT ", SELECT_FIELD, " FROM ", TABLE_NAME, " WHERE id =#{id}"})
    Question getQuestionById(int id);

    List<Question> getLastedQuestion(@Param("userId") int userId,
                                     @Param("offset") int offset,
                                     @Param("limit") int limit);

    @Update({"update ",TABLE_NAME, " set comment_count=#{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
