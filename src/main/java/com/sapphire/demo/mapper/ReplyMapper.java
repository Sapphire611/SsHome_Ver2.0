package com.sapphire.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.*;

import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.Reply;

@Mapper
public interface ReplyMapper {
    
	@Insert("Insert into Reply (questionId,userId,description,gmtCreate,gmtModified) values (#{questionId},#{userId},#{description},#{gmtCreate},#{gmtModified})")
    void insert(Reply reply);

	@Select("select * from reply where questionId = #{questionId} order by gmtCreate limit #{offset},#{size}")
	List<Reply> listByQuestionId(@Param(value = "questionId")Integer questionId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
	
	@Select("select count(1) from reply where questionId = #{questionId} order by gmtCreate")
    Integer countByQuestionId(@Param(value = "questionId")Integer questionId);
	
	@Select("select * from reply where userId = #{userId} order by gmtCreate desc limit #{offset},#{size}")
	List<Reply> listByUserId(@Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
	
	@Select("select count(1) from reply where userId = #{userId} order by gmtCreate desc")
	Integer countByUserId(@Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}

