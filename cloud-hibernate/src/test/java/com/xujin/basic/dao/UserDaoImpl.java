package com.xujin.basic.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.xujin.base.dao.BaseDaoImpl;
import org.xujin.base.model.Pager;

import com.xujin.basic.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


	@Override
	public List<User> listUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return super.listBySql(string, objects, alias, class1, b);
	}

	@Override
	public List<User> listUserBySql(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return super.listBySql(string, objects, class1, b);
	}

	@Override
	public Pager<User> findUserBySql(String string, Object[] objects,
			Class<User> class1, boolean b) {
		return super.findBySql(string, objects, class1, b);
	}

	@Override
	public Pager<User> findUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b) {
		return super.findBySql(string, objects, alias, class1, b);
	}
}
