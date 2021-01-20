package com.sapphire.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sapphire.demo.model.Admin;

@Mapper
public interface AdminMapper {
	
	@Select("select * from admin where name = #{userName} and password = #{password}")
    Admin loginByUserName(@Param("userName") String userName,@Param("password") String password);

}
