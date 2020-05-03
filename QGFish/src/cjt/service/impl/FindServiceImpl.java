package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.User;
import cjt.service.FindService;

/**
 * @author cjt
 */
public class FindServiceImpl implements FindService {
    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @Override
    public User findUser(String userId) {
        User user =new User();
        user.setUserId(Integer.parseInt(userId));
        FindDao findDao = new FindDaoImpl();
        //通过数据库查询用户完整信息
        return findDao.findUser(user);
    }
}
