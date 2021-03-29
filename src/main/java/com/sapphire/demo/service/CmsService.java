package com.sapphire.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
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
import com.sapphire.demo.model.NotificationExample;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.QuestionExample;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;

@Service
public class CmsService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private LikeRecordMapper likeRecordMapper;
	
	@Autowired
	private NotificationMapper notificationMapper;

	// CMS 页面显示 Users
	public PaginationDTO<User> userList(Integer page, Integer size) {
		PaginationDTO<User> paginationDTO = new PaginationDTO<User>();

		// count()
		UserExample example = new UserExample();
		Integer totalCount = (int) userMapper.countByExample(example);

		Integer totalPage;

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		// size * (page - 1)
		Integer offset = page < 1 ? 0 : size * (page - 1);

		if (totalCount != 0) {
			UserExample example2 = new UserExample();
			paginationDTO.setData(userMapper.selectByExampleWithRowbounds(example2, new RowBounds(offset, size)));
		}

		return paginationDTO;
	}

	// CMS 页面显示 Question
	public PaginationDTO<QuestionDTO> questionList(Integer page, Integer size) {
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();

		// count()
		QuestionExample example = new QuestionExample();
		Integer totalCount = (int) questionMapper.countByExample(example);

		paginationDTO.setPagination(totalCount, page);

		Integer totalPage;

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		// size * (page - 1)
		Integer offset = page < 1 ? 0 : size * (page - 1);

		List<Question> questions = new ArrayList<Question>();
		if (totalCount != 0) {
			QuestionExample example2 = new QuestionExample();
			questions = questionMapper.selectByExampleWithRowbounds(example2, new RowBounds(offset, size));
		}

		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}

		paginationDTO.setData(questionDTOList);

		return paginationDTO;
	}

	// CMS 页面显示 Comment
	public PaginationDTO<Comment> commentList(Integer page, Integer size) {
		PaginationDTO<Comment> paginationDTO = new PaginationDTO<Comment>();

		// count()
		CommentExample example = new CommentExample();
		Integer totalCount = (int) commentMapper.countByExample(example);

		paginationDTO.setPagination(totalCount, page);

		Integer totalPage;

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		// size * (page - 1)
		Integer offset = page < 1 ? 0 : size * (page - 1);

		List<Comment> comments = new ArrayList<Comment>();
		if (totalCount != 0) {
			CommentExample commentExapmle = new CommentExample();
			comments = commentMapper.selectByExampleWithRowbounds(commentExapmle, new RowBounds(offset, size));
		}

		paginationDTO.setData(comments);
		return paginationDTO;
	}

	// CMS 页面显示 likeRecord
	public PaginationDTO <LikeRecord> likeRecordList(Integer page, Integer size) {
		PaginationDTO <LikeRecord> paginationDTO = new PaginationDTO<LikeRecord>();

		// count()
		LikeRecordExample example = new LikeRecordExample();
		Integer totalCount = (int) likeRecordMapper.countByExample(example);

		paginationDTO.setPagination(totalCount, page);

		Integer totalPage;

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		// size * (page - 1)
		Integer offset = page < 1 ? 0 : size * (page - 1);

		List<LikeRecord> likeRecords = new ArrayList<LikeRecord>();
		if (totalCount != 0) {
			LikeRecordExample example2 = new LikeRecordExample();
			likeRecords = likeRecordMapper.selectByExampleWithRowbounds(example2, new RowBounds(offset, size));
		}

		paginationDTO.setData(likeRecords);
		return paginationDTO;
	}

	// CMS 页面显示 Notification
	public PaginationDTO<Notification> notificationList(Integer page, Integer size) {
		PaginationDTO<Notification> paginationDTO = new PaginationDTO<Notification>();

		// count()
		NotificationExample example = new NotificationExample();
		Integer totalCount = (int) notificationMapper.countByExample(example);

		paginationDTO.setPagination(totalCount, page);

		Integer totalPage;

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		// size * (page - 1)
		Integer offset = page < 1 ? 0 : size * (page - 1);

		List<Notification> notifications = new ArrayList<Notification>();
		if (totalCount != 0) {
			NotificationExample example2 = new NotificationExample();
			notifications = notificationMapper.selectByExampleWithRowbounds(example2, new RowBounds(offset, size));
		}

		paginationDTO.setData(notifications);
		return paginationDTO;
	}

}
