package com.xujin.basic.dao;

import java.util.List;
import java.util.Map;

import org.xujin.base.dao.BaseDao;
import org.xujin.base.model.Pager;

import com.xujin.basic.model.User;

public interface UserDao extends BaseDao<User> {

    List<User> list(String string, Object[] objects);

    List<User> list(String string, Object[] objects, Map<String, Object> alias);

    Pager<User> find(String string, Object[] objects);

    Pager<User> find(String string, Object[] objects, Map<String, Object> alias);

    List<User> listUserBySql(String string, Object[] objects, Map<String, Object> alias, Class<User> class1, boolean b);

    List<User> listUserBySql(String string, Object[] objects, Class<User> class1, boolean b);

    Pager<User> findUserBySql(String string, Object[] objects, Class<User> class1, boolean b);

    Pager<User> findUserBySql(String string, Object[] objects, Map<String, Object> alias, Class<User> class1, boolean b);

}
