package com.sapphire.demo.service;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.ReplyMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.Reply;
import com.sapphire.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {
	
	@Autowired
    private UserMapper userMapper;
	
	@Autowired
    private QuestionMapper questionMapper;
	
    @Autowired
    private ReplyMapper replyMapper;

    // Question 页面显示  Reply
    public PaginationDTO list(Integer questionId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = replyMapper.countByQuestionId(questionId);
        if(totalCount != 0) {
        	
            paginationDTO.setPagination(totalCount, page, size);
            // 当前页小于1或者大于totalPage时,做出修正
            if (page < 1) page = 1;
            if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();


            // size * (page - 1)
            Integer offset = size * (page - 1);
        	List<Reply> replies = replyMapper.listByQuestionId(questionId, offset, size);
            List<ReplyDTO> repliesDTOList = new ArrayList<>();

            for (Reply reply : replies) {
                User user = userMapper.findById(reply.getUserId());
                ReplyDTO replyDTO = new ReplyDTO();
                BeanUtils.copyProperties(reply, replyDTO);
                replyDTO.setUser(user);
                repliesDTOList.add(replyDTO);
            }

            paginationDTO.setReplies(repliesDTOList);
        }
        

        return paginationDTO;
    }

    
    // Profile 页面显示 My Reply
    public PaginationDTO listAtProfile(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = replyMapper.countByUserId(userId,page, size);
        if(totalCount != 0) {
        	
            paginationDTO.setPagination(totalCount, page, size);
            // 当前页小于1或者大于totalPage时,做出修正
            if (page < 1) page = 1;
            if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();


            // size * (page - 1)
            Integer offset = size * (page - 1);
        	List<Reply> replies = replyMapper.listByUserId(userId, offset, size);
            List<ReplyDTO> repliesDTOList = new ArrayList<>();

            for (Reply reply : replies) {
                User user = userMapper.findById(reply.getUserId());
                String questionTitle = questionMapper.findNameById(reply.getQuestionId());
                ReplyDTO replyDTO = new ReplyDTO();
                BeanUtils.copyProperties(reply, replyDTO);
                replyDTO.setUser(user);
                replyDTO.setQuestionTitle(questionTitle);
                repliesDTOList.add(replyDTO);
            }

            paginationDTO.setReplies(repliesDTOList);
        }
        

        return paginationDTO;
    }
    
    // Notice 用于
    public PaginationDTO listAtNotice(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = replyMapper.countByUserId(userId,page, size);
        if(totalCount != 0) {
        	
            paginationDTO.setPagination(totalCount, page, size);
            // 当前页小于1或者大于totalPage时,做出修正
            if (page < 1) page = 1;
            if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();


            // size * (page - 1)
            Integer offset = size * (page - 1);
        	List<Reply> replies = replyMapper.listNoticeById(userId, offset, size);
            List<ReplyDTO> repliesDTOList = new ArrayList<>();

            for (Reply reply : replies) {
                User user = userMapper.findById(reply.getUserId());
                String questionTitle = questionMapper.findNameById(reply.getQuestionId());
                ReplyDTO replyDTO = new ReplyDTO();
                BeanUtils.copyProperties(reply, replyDTO);
                replyDTO.setUser(user);
                replyDTO.setQuestionTitle(questionTitle);
                replyDTO.setGmtQuestionRead(questionMapper.getAuthorReadTimeById(reply.getQuestionId()));
                repliesDTOList.add(replyDTO);
            }

            paginationDTO.setReplies(repliesDTOList);
        }
        

        return paginationDTO;
    }
  
}
