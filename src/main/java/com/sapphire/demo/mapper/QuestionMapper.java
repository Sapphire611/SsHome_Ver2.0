package com.sapphire.demo.mapper;

import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.ViewRecord;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmtCreate,gmtModified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    // 传入非本地参数时，需要手动添加映射
    @Select("select * from question order by id desc limit #{offset},#{size} ")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} order by id desc limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId")Integer userId,@Param(value = "offset")  Integer offset, Integer size);

    @Select("select * from question where creator = #{userId} order by id desc")
    List<Question> listByUser(@Param(value = "userId")Integer userId);

    @Select("select count(1) from question where creator = #{userId} order by id desc")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select creator from question where id = #{id}")
    Integer getCreatorById(@Param(value = "id")Integer id);
    
    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id")Integer id);
    
    @Select("select q.*,r.questionId  from question q ,reply r where q.id = r.questionId and q.id = #{id};")
    Question getById2(@Param(value = "id")Integer id);
    
    @Update("update question set title = #{title},description = #{description},gmtModified = #{gmtModified},tag = #{tag} where id = #{id}")
    void update(Question question);
    
    @Update("update question set gmtAuthorRead = #{readTime} where id = #{id}")
    void updateAuthorReadTime(@Param(value = "readTime")Long readTime,@Param(value = "id")Integer id);
    
    @Select("select * from viewRecord where userId = #{userID} and questionId = #{questionId}")
    ViewRecord checkViewQuestion(@Param(value = "questionId")Integer questionId,@Param(value = "userID") Integer userID);
  
    @Select("select * from likeRecord where userId = #{userID} and questionId = #{questionId}")
    LikeRecord checkLikeQuestion(@Param(value = "questionId")Integer questionId,@Param(value = "userID") Integer userID);

    @Insert("insert into likeRecord(userId,questionId,gmtCreate) values(#{userId},#{questionId},#{gmtCreate})")
	void likeQuestion(LikeRecord likeRecord);

    @Insert("insert into ViewRecord(userId,questionId,gmtCreate) values(#{userId},#{questionId},#{gmtCreate})")
	void viewQuestion(ViewRecord viewRecord);
    
    @Update("update question set view_count = view_count + 1 where id = #{id};")
    void viewAdd(@Param(value = "id")Integer id);
    
    @Update("update question set like_count = like_count + 1 where id = #{id};")
    void likeAdd(@Param(value = "id")Integer id);
    
    @Update("update question set comment_count = comment_count + 1 where id = #{id};")
    void commentAdd(@Param(value = "id")Integer id);
    
    @Update("update question set comment_count = comment_count - 1 where id = #{id};")
    void commentMinus(@Param(value = "id")Integer id);
    
    @Select("select title from question where id = #{questionId}")
    String findNameById(@Param(value = "questionId")Integer questionId);

    @Delete("delete from question where id = #{id}")
    void deleteQuestion(@Param(value = "id")Integer id);
    
    @Select("select gmtAuthorRead from question where id = #{id}")
    Long getAuthorReadTimeById(@Param(value = "id")Integer id);
	
}

