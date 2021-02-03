package com.sapphire.demo.service;

import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : Sapphire L
 * @Date : 2020/6/3 1:23 上午
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        // User dbUser = userMapper.findByaccountId(user.getAccountId());
    	UserExample userExample = new UserExample();
    	userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
    	List<User> users = userMapper.selectByExample(userExample);
    	if(users.size() == 0){
            //insert
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
        }else{
        	// update
            // dbUser = 取查询到到第一个结果（应该也只有一个结果
        	User dbUser = users.get(0);
        	
        	UserExample example = new UserExample();
        	example.createCriteria().andIdEqualTo(dbUser.getId());
        	
        	User updatedUser = new User();
        	updatedUser.setGmtmodified(System.currentTimeMillis());
        	updatedUser.setName(user.getName());
        	updatedUser.setToken(user.getToken());
        	updatedUser.setAvatarurl(user.getAvatarurl());
            userMapper.updateByExampleSelective(updatedUser, userExample);
        }
    }
}
