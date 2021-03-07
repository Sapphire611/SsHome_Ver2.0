package com.sapphire.demo.service;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.QuestionExample;
import com.sapphire.demo.model.User;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	public PaginationDTO list(Integer page, Integer size) {

		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
		paginationDTO.setPagination(totalCount, page, size);
		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > paginationDTO.getTotalPage())
			page = paginationDTO.getTotalPage();

		// size * (page - 1)
		Integer offset = size * (page - 1);

		QuestionExample example = new QuestionExample();
		example.setOrderByClause("gmtModified desc");
		List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOList);

		return paginationDTO;
	}

	// Profile 页面显示 My Question
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();

		// count()
		QuestionExample example = new QuestionExample();
		example.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int) questionMapper.countByExample(example);

		paginationDTO.setPagination(totalCount, page, size);
		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > paginationDTO.getTotalPage())
			page = paginationDTO.getTotalPage();

		// size * (page - 1)
		Integer offset = size * (page - 1);

		List<Question> questions = new ArrayList<Question>();
		if (totalCount != 0) {
			QuestionExample example2 = new QuestionExample();
			example2.createCriteria().andCreatorEqualTo(userId);
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

		paginationDTO.setQuestions(questionDTOList);

		return paginationDTO;
	}

	public QuestionDTO getById(Long id) {
		Question question = questionMapper.selectByPrimaryKey(id);

		// 处理/question/[id]不存在的情况
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}

		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		questionDTO.setUser(user);

		return questionDTO;
	}

	public void createOrUpdate(Question question) {
		if (question.getId() == null) {
			question.setGmtcreate(System.currentTimeMillis());
			question.setGmtmodified(question.getGmtcreate());
			question.setCommentCount(0);
			question.setViewCount(0);
			question.setLikeCount(0);

			questionMapper.insert(question);
		} else {
			question.setGmtmodified(question.getGmtcreate());

			Question updateQuestion = new Question();
			updateQuestion.setTitle(question.getTitle());
			updateQuestion.setDescription(question.getDescription());
			updateQuestion.setTag(question.getTag());
			updateQuestion.setGmtmodified(System.currentTimeMillis());

			QuestionExample example = new QuestionExample();
			example.createCriteria().andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(updateQuestion, example);

			if (updated != 1)
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
	}
}
