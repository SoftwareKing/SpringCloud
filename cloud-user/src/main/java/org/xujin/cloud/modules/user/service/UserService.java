/**  
* @Title: UserService.java
* @Package org.xujin.cloud.modules.user.service
* @Description: TODO
* @author Xu,Jin Software_King@qq.com  
* @date 2015年4月19日 上午11:53:17
* @version V1.0
*/ 
package org.xujin.cloud.modules.user.service;

import org.xujin.core.entity.User;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author Xu,Jin Software_King@qq.com
 * @date 2015年4月19日 上午11:53:17
 *
 */
public interface UserService {
	
	public void findList();
	public void saveUser(User user);
	public void updateUser(User user);
	public void deleteUser(int id);
	

}
