package com.sapphire.demo.mapper;

import java.util.List;

import com.sapphire.demo.model.Question;

public interface QuestionExtMapper {
	
	List<Question> selectRelated(Question question);
	
	List<Question> selectHot();
}
