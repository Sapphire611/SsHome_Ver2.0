package com.sapphire.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapphire.demo.enums.CommentTypeEnum;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.CommentMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.Question;

@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private QuestionMapper questionMapper;

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

}
