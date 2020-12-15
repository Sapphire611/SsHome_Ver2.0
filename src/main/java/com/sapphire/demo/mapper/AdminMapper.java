package com.sapphire.demo.mapper;

import com.sapphire.demo.model.Admin;

import java.util.*;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
	
	@Select("select * from admin where name = #{userName} and password = #{password}")
    Admin loginByUserName(@Param("userName") String userName,@Param("password") String password);

}
