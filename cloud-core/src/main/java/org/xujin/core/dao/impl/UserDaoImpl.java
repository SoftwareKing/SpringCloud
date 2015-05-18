/**  
 * @Title: UserDaoImpl.java
 * @Package org.xujin.core.dao.impl
 * @Description: TODO
 * @author Xu,Jin Software_King@qq.com  
 * @date 2015年4月19日 上午11:49:06
 * @version V1.0
 */
package org.xujin.core.dao.impl;

import org.springframework.stereotype.Repository;
import org.xujin.base.dao.BaseDaoImpl;
import org.xujin.core.dao.UserDao;
import org.xujin.core.entity.User;

/**
 * @ClassName: UserDaoImpl
 * @Description: TODO
 * @author Xu,Jin Software_King@qq.com
 * @date 2015年4月19日 上午11:49:06
 *
 */
@Repository("UserDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

}
