package com.sapphire.demo.controller;

import com.sapphire.demo.dto.QuestionDTO;
import com.sapphire.demo.mapper.QuestionMapper;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.Question;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id,Model model,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/login";
        }else{
            // ID = question.id
            // 通过get传来的id，找到对应问题的id,再查询对应问题的发布者
            //Question byId = questionMapper.getById(id);
            //Integer creator = byId.getCreator();
            //User publisher = userMapper.findById(creator);
            //User publisher = userMapper.findById(creator);

            QuestionDTO question = questionService.getById(id);

            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("tag",question.getTag());
            model.addAttribute("id",question.getId());

            return "/publish";
        }


    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
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


        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","Please Login first...");
            return "publish";
        }


        Question question = new Question();
        question.setTitle(title);
        question.setId(id);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);

        return "redirect:/";
    }


}
