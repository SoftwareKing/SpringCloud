package org.xujin.cloud.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xujin.cloud.modules.user.service.UserService;
import org.xujin.core.dao.UserDao;
import org.xujin.core.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userdao;

	public void findList() {
		
	}

	public void saveUser(User user) {

		this.userdao.add(user);
	}

	public void updateUser(User user) {

		this.userdao.update(user);
	}

	public void deleteUser(int id) {

		this.userdao.delete(id);
	}

}
