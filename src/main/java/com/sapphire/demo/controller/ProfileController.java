package com.sapphire.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import com.sapphire.demo.service.ReplyService;

@Controller
public class ProfileController {

	static private List<Integer> newNoticeNumberList;
	
	@Autowired
    private QuestionMapper questionMapper;
	
	@Autowired
    private QuestionService questionService;
    
    @Autowired
    private ReplyService replyService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "7") Integer size,
                          HttpServletRequest request,
                          Model model) {

        //User user = (User) model.getAttribute("user");
        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            return "redirect:/login";
        }

        if ("questions".equals(action)) {
        	// Question 页面
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "My Questions");
            
            PaginationDTO paginationQuestionDTO = questionService.list(currentUser.getId(),page,size);
            model.addAttribute("paginationDTO",paginationQuestionDTO);
        } else if("replies".equals(action)){
        	//Reply 页面
        	PaginationDTO paginationQuestionDTO = replyService.listAtProfile(currentUser.getId(),page,size);
            model.addAttribute("paginationDTO",paginationQuestionDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "Replies");
        } else if("personalInfo".equals(action)) {
            return "redirect:/userinfo/" + currentUser.getName();
        }else if("settings".equals(action)){
        	model.addAttribute("section", "settings");
            model.addAttribute("sectionName", "My Settings");
        	
        	User infoUser = currentUser;
			model.addAttribute("infoUser",infoUser);
			return "settings";
        }else if("notices".equals(action)){
        	model.addAttribute("section", "notices");
            model.addAttribute("sectionName", "My notices");
            
            // 设置分页
        	PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(),page,size);
            model.addAttribute("paginationDTO",paginationQuestionDTO);  
            
            int countNewNotice = 0;
            for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
				if(reply.getGmtCreate() > reply.getGmtQuestionRead()) {
					countNewNotice ++ ;
				}
			}
            
            model.addAttribute("countNewNotice",countNewNotice);
        }

        return "profile";
    }

    
    //测试失败，用不来
    @GetMapping("/getNoticeNumber")
	@ResponseBody
	List<Integer> getNoticeNumber(HttpServletRequest request)
	{
    	User currentUser = (User) request.getSession().getAttribute("user");
    	PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(),1,7);

    	int newNoticeNumber = 0;
        for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
			if(reply.getGmtCreate() > reply.getGmtQuestionRead()) {
				newNoticeNumber++ ;
			}
		}
	
        newNoticeNumberList.add(newNoticeNumber);
		return newNoticeNumberList;		
	}
   
}
