package com.sapphire.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapphire.demo.dto.CommentDTO;
import com.sapphire.demo.enums.CommentTypeEnum;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.CommentMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.CommentExample;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Transactional
	public void insert(Comment comment) {
		// 确保 ParentId 存在
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}

		// 确保 type 存在
		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}

		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论

			// 确保回复本身还在
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			commentMapper.insert(comment);

		} else {
			// 回复问题

			// 确保问题本身还在
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}

			commentMapper.insert(comment);
			
			
			// 问题的回复数 + 1 ??
			question.setCommentCount(question.getCommentCount() + 1);
			questionMapper.updateByPrimaryKey(question);

		}

	}

	public List<CommentDTO> listByQuestionId(Long id) {
		// 获取对应问题id下的一级回复 
		CommentExample example = new CommentExample();
		example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
		List<Comment> comments = commentMapper.selectByExample(example);
		
		if(comments.size() == 0) {
			return new ArrayList<>();
		}
		
		// 获得去重的评论人
		Set <Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
		
		// 获得所有评论人
		ArrayList<Long> userIds = new ArrayList<Long>();
		userIds.addAll(commentators);
		
		UserExample userExample = new UserExample();
		userExample.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);
		
		// 转换 comment 为 CommentDTO
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
		
		List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			
			return commentDTO;
		}).collect(Collectors.toList());
		
		return commentDTOs;
	}

}
