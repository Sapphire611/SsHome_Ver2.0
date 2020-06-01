package com.sapphire.demo.controller;

import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title == ""){
            model.addAttribute("error","Please input Title...");
            return "/publish";
        }

        if(description == null || description == ""){
            model.addAttribute("error","Please input Description...");
            return "/publish";
        }

        if(tag == null || tag == ""){
            model.addAttribute("error","Please input tags...");
            return "/publish";
        }


        User user = (User) request.getAttribute("user");
        if(user == null){
            model.addAttribute("error","Please Login first...");
            return "publish";
        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }


}
