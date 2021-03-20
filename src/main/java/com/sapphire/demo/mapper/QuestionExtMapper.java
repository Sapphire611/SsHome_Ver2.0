package com.sapphire.demo.mapper;

import java.util.List;

import com.sapphire.demo.dto.QuestionQueryDTO;
import com.sapphire.demo.model.Question;

public interface QuestionExtMapper {
	
	List<Question> selectRelated(Question question);
	
	List<Question> selectHot();
	
	Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
