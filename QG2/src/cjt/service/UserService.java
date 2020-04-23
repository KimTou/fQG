package cjt.service;

import cjt.model.ResultInfo;

/**
 * @author cjt
 * 管理用户业务的接口
 */
public interface UserService {
    /**
     * 用户登录
     * @param userName
     * @param password
     * @param checkCode
     * @param checkCode_session
     * @return
     */
    public ResultInfo login(String userName,String password,String checkCode,String checkCode_session);

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param email
     * @param checkCode
     * @param checkCode_session
     * @return
     */
    public ResultInfo register(String userName,String password,String email,String checkCode,String checkCode_session);
}
