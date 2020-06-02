package com.sapphire.demo.mapper;

import com.sapphire.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("Insert into User (name,accountId,token,gmtCreate,gmtModified,bio,avatarUrl,password) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl},#{password})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where accountId = #{accountId} and password = #{password}")
    User login(@Param("accountId") String accountId,@Param("password") String password);

    @Select("select * from user where accountId = #{accountId}")
    User findByaccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},token = #{token},gmtModified = #{gmtModified},avatarUrl = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}

