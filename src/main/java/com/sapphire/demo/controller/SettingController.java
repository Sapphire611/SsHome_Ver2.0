package com.sapphire.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sapphire.demo.dto.PaginationDTO;
import com.sapphire.demo.dto.ReplyDTO;
import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.service.ReplyService;

@Controller
public class SettingController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/profile/settings/changeInfo")
	public String changeUserInfo(@RequestParam(value = "bio", required = false) String bio, HttpServletRequest request,
			Model model) {

		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			String currentUserBio = currentUser.getBio();

			if (bio == null || bio == "") {
				model.addAttribute("error1", "Bio can not be null...");
			} else if (bio.equals(currentUserBio)) {
				model.addAttribute("error1", "The new bio cannot be the same as the old bio...");
			} else {
				currentUser.setBio(bio.trim());
				userMapper.updateUserInfo(currentUser);
				model.addAttribute("success1", "Update Bio success !!!");
			}
			model.addAttribute("bio", bio);
			model.addAttribute("section", "settings");
			model.addAttribute("sectionName", "My Settings");
			model.addAttribute("infoUser", currentUser);

			// 显示新消息数
			if (currentUser != null) {
				PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(), 1, 7);
				int countNewNotice = 0;
				for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
					if (reply.getGmtCreate() > reply.getGmtQuestionRead()) {
						countNewNotice++;
					}
				}
				model.addAttribute("countNewNotice", countNewNotice);
			}
			// 显示新消息数 End

			return "settings";
		}

	}

	@PostMapping("/profile/settings/changePassword")
	public String changeUserPassword(@RequestParam(value = "oldPassword", required = false) String oldPassword,
			@RequestParam(value = "newPassword", required = false) String newPassword,
			@RequestParam(value = "newPasswordAgain", required = false) String newPasswordAgain,
			HttpServletRequest request, Model model) {

		// User user = (User) model.getAttribute("user");
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser == null) {
			return "redirect:/";
		} else {
			String currentUserPassword = currentUser.getPassword();

			if (oldPassword == null || oldPassword == "") {
				model.addAttribute("error2", "OldPassword can not be null...");
			} else if (newPassword == null || newPassword == "") {
				model.addAttribute("error2", "NewPassword can not be null...");
			} else if (newPasswordAgain == null || newPasswordAgain == "") {
				model.addAttribute("error2", "NewPasswordAgain can not be null...");
			} else if (!newPassword.equals(newPasswordAgain)) {
				// 新旧密码不一
				model.addAttribute("error2", "The new newPassword are not equal as newPasswordAgain.");
			} else if (!currentUserPassword.equals(oldPassword)) {
				// 旧密码不正确
				model.addAttribute("error2", "The oldPassword are wrong...");
			} else if (newPassword.length() < 6 || newPassword.length() > 16) {
				// 密码小于6位或大于16位
				model.addAttribute("error2", "The length of NewPassword should between 6 and 16 !");
			} else {
				currentUser.setPassword(newPassword);
				userMapper.updateUserPassword(currentUser);
				model.addAttribute("success2", "Update Password success !!!");
			}

			model.addAttribute("oldPassword", oldPassword);
			model.addAttribute("newPassword", newPassword);
			model.addAttribute("newPasswordAgain", newPasswordAgain);

			model.addAttribute("section", "settings");
			model.addAttribute("sectionName", "My Settings");
			model.addAttribute("infoUser", currentUser);

			// 显示新消息数
			if (currentUser != null) {
				PaginationDTO paginationQuestionDTO = replyService.listAtNotice(currentUser.getId(), 1, 7);
				int countNewNotice = 0;
				for (ReplyDTO reply : paginationQuestionDTO.getReplies()) {
					if (reply.getGmtCreate() > reply.getGmtQuestionRead()) {
						countNewNotice++;
					}
				}
				model.addAttribute("countNewNotice", countNewNotice);
			}
			// 显示新消息数 End
			return "settings";
		}

	}
}
