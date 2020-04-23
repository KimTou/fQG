package cjt.dao;

import cjt.model.ResultInfo;
import cjt.model.User;

/**
 * @author cjt
 * 与数据库交互的接口
 */
public interface UserDao {
    /**
     * 用户登录
     * @param user
     * @return
     */
    public ResultInfo login(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    public ResultInfo register(User user);
}
