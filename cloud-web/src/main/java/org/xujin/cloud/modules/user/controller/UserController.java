package org.xujin.cloud.modules.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("admin/user")
@Controller
public class UserController {
	
	@RequestMapping("/add")
	public String addUser()
	{
		return "/user/user_add";
	}

	

}
