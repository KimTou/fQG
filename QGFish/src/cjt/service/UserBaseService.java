package cjt.service;

import cjt.model.Appeal;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @author cjt
 * 用户基础功能业务（个人信息，商品信息）
 */
public interface UserBaseService {
    /**
     * 用户登陆
     * @param user
     * @return
     */
    public ResultInfo login(User user);

    /**
     * 用户注册
     * @param user
     * @param checkCode_session
     * @return
     */
    public ResultInfo register(User user,String checkCode_session);




    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public ResultInfo update(User user);

    /**
     * 用户修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword1
     * @param newPassword2
     * @return
     */
    public ResultInfo updatePassword(int userId,String oldPassword,String newPassword1,String newPassword2);

    /**
     * 用户忘记密码，找回密码
     * @param email
     * @return
     */
    public ResultInfo findBackPassword(String email);

    /**
     * 卖家上传商品
     * @param product
     * @return
     */
    public ResultInfo release(Product product);

    /**
     * 卖家上传商品图片
     * @param productId
     * @param filePart
     * @param realPath
     * @param filename
     * @return
     * @throws IOException
     */
    public boolean releasePicture(String productId, Part filePart, String realPath, String filename) throws IOException;


    /**
     * 查看商品完整信息
     * @param product
     * @return
     */
    public ResultInfo read(Product product);

    /**
     * 分页模糊查找商品
     * @param userId
     * @param currentPage
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    public ResultInfo findProductByPage(int userId,int currentPage, String likeProductName, String likeKind, String radio);



}
