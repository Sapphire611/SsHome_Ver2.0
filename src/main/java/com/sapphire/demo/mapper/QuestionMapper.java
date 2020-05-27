package com.sapphire.demo.mapper;

import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmtCreate,gmtModified,creator,tag，avatarUrl) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}，#{avatarUrl})")
    void create(Question question);
}

