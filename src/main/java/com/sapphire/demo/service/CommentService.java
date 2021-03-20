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
import com.sapphire.demo.enums.NotificationStatusEnum;
import com.sapphire.demo.enums.NotificationTypeEnum;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.CommentMapper;
import com.sapphire.demo.mapper.LikeRecordMapper;
import com.sapphire.demo.mapper.NotificationMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Comment;
import com.sapphire.demo.model.CommentExample;
import com.sapphire.demo.model.LikeRecord;
import com.sapphire.demo.model.LikeRecordExample;
import com.sapphire.demo.model.Notification;
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

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private LikeRecordMapper likeRecordMapper;

	@Transactional
	public void insert(Comment comment, User commentator) {
		// 确保 ParentId 存在
		Long parentId = comment.getParentId();
		if (parentId == null || parentId == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}

		// 确保 type 存在
		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}

		if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
			// 回复评论

			// 确保回复本身还在
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}

			// 确保问题本身还在
			Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}

			// 二级回复数 + 1
			dbComment.setCommentcount(dbComment.getCommentcount() + 1);
			commentMapper.updateByPrimaryKey(dbComment);

			// 插入回复
			commentMapper.insert(comment);

			// 创建通知
			createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(),
					NotificationTypeEnum.REPLY_COMMENT, question.getId());

		} else {
			// 回复问题

			// 确保问题本身还在
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}

			commentMapper.insert(comment);

			// 问题的回复数 + 1
			question.setCommentCount(question.getCommentCount() + 1);
			questionMapper.updateByPrimaryKey(question);

			// 创建通知
			createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(),
					NotificationTypeEnum.REPLY_QUESTION, question.getId());

		}

	}

	public List<CommentDTO> listByQuestionId(CommentTypeEnum type, Long id, User currentUser) {
		// 获取对应问题id下的一级回复
		CommentExample example = new CommentExample();
		example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
		List<Comment> comments = commentMapper.selectByExample(example);

		if (comments.size() == 0) {
			return new ArrayList<>();
		}

		// 获得去重的评论人
		Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

		// 获得所有评论人
		ArrayList<Long> userIds = new ArrayList<Long>();
		userIds.addAll(commentators);

		UserExample userExample = new UserExample();
		userExample.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);

		// 转换 comment 为 CommentDTO
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

		List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(userMap.get(comment.getCommentator()));

			// 增加判断 当前用户是否点赞当前评论
			LikeRecordExample example2 = new LikeRecordExample();
			example2.createCriteria().andUseridEqualTo(currentUser.getId()).andSourceidEqualTo(comment.getId())
					.andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
			List<LikeRecord> selectByExample2 = likeRecordMapper.selectByExample(example2);

			if (selectByExample2.size() == 0) {
				commentDTO.setIsLiked(0);
			} else {
				commentDTO.setIsLiked(1);
			}
			return commentDTO;
		}).collect(Collectors.toList());

		return commentDTOs;
	}

	// 手动复制了一遍
	public List<CommentDTO> listByTargetId(CommentTypeEnum type, Long id) {
		// 获取对应问题id下的一级回复
		CommentExample example = new CommentExample();
		example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
		example.setOrderByClause("gmtCreate desc");
		List<Comment> comments = commentMapper.selectByExample(example);

		if (comments.size() == 0) {
			return new ArrayList<>();
		}

		// 获得去重的评论人
		Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

		// 获得所有评论人
		ArrayList<Long> userIds = new ArrayList<Long>();
		userIds.addAll(commentators);

		UserExample userExample = new UserExample();
		userExample.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);

		// 转换 comment 为 CommentDTO
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

		List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);

			commentDTO.setUser(userMap.get(comment.getCommentator()));

			return commentDTO;
		}).collect(Collectors.toList());

		return commentDTOs;
	}

	private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle,
			NotificationTypeEnum notificationType, Long outerId) {
		// 不用给自己发通知
		if (receiver == comment.getCommentator()) {
			return;
		}
		Notification notification = new Notification();
		notification.setGmtcreate(System.currentTimeMillis());
		notification.setType(notificationType.getType());
		notification.setOuterid(outerId);
		notification.setNotifier(comment.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver);
		notification.setNotifiername(notifierName);
		notification.setOutertitle(outerTitle);
		notificationMapper.insert(notification);
	}
}
