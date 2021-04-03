package com.sapphire.demo.service;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.dto.QuestionQueryDTO;
import com.sapphire.demo.exception.CustomizeErrorCode;
import com.sapphire.demo.exception.CustomizeException;
import com.sapphire.demo.mapper.QuestionExtMapper;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.QuestionExample;
import com.sapphire.demo.model.User;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private QuestionExtMapper questionExtMapper;

	public PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size) {

		if (StringUtils.isNotBlank(search)) {
			String[] tags = StringUtils.split(search, " ");
			search = Arrays.stream(tags).filter(StringUtils::isNotBlank)
					.map(t -> t.replace("+", "").replace("*", "").replace("?", "")).filter(StringUtils::isNotBlank)
					.collect(Collectors.joining("|"));
		}

		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();
		Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

		Integer totalPage;
		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		// Search
		QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
		questionQueryDTO.setSearch(search);	

		Integer SearchtotalCount = questionExtMapper.countBySearch(questionQueryDTO);

		if (SearchtotalCount % size == 0) {
			totalPage = SearchtotalCount / size;
		} else {
			totalPage = SearchtotalCount / size + 1;
		}

		Integer offset = size * (page - 1);
		
		// 当前页小于1或者大于totalPage时,做出修正
		if (page < 1)
			page = 1;
		if (page > totalPage)
			page = totalPage;

		paginationDTO.setTotalCount(totalCount);
		paginationDTO.setPagination(totalPage, page);

		// size * (page - 1)
		
		questionQueryDTO.setSize(size);
		questionQueryDTO.setPage(offset);

		QuestionExample example = new QuestionExample();
		example.setOrderByClause("gmtModified desc");
//		List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
//		List<QuestionDTO> questionDTOList = new ArrayList<>();

		// search
		List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
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

	// Profile 页面显示Question
	public PaginationDTO<QuestionDTO> list(Long userId, Integer page, Integer size) {
		PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<QuestionDTO>();

		// count()
		QuestionExample example = new QuestionExample();
		example.createCriteria().andCreatorEqualTo(userId);
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

		paginationDTO.setData(questionDTOList);

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

	public int createOrUpdate(Question question) {
		if (question.getId() == null) {
			question.setGmtcreate(System.currentTimeMillis());
			question.setGmtmodified(question.getGmtcreate());
			question.setCommentCount(0);
			question.setViewCount(0);
			question.setLikeCount(0);

			questionMapper.insert(question);
			return 1;
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

		return 2;
	}

	public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
		if (StringUtils.isBlank(queryDTO.getTag())) {
			return new ArrayList<>();
		}
		String[] tags = StringUtils.split(queryDTO.getTag(), ",");
		String regexpTag = Arrays.stream(tags).filter(StringUtils::isNotBlank)
				.map(t -> t.replace("+", "").replace("*", "").replace("?", "")).filter(StringUtils::isNotBlank)
				.collect(Collectors.joining("|"));
		Question question = new Question();
		question.setId(queryDTO.getId());
		question.setTag(regexpTag);

		List<Question> questions = questionExtMapper.selectRelated(question);
		List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(q, questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());
		return questionDTOS;
	}
}
