package cjt.service;

import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @author cjt
 */
public interface UserService {
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
     * 卖家上传商品
     * @param product
     * @return
     */
    public ResultInfo release(Product product);


    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public ResultInfo update(User user);

    /**
     * 用户修改密码
     * @param oldPassword
     * @param newPassword1
     * @param newPassword2
     * @return
     */
    public ResultInfo updatePassword(String userId,String oldPassword,String newPassword1,String newPassword2);

    /**
     * 卖家上传商品图片
     * @param productId
     * @param filePart
     * @param realPath
     * @param filename
     * @return
     * @throws IOException
     */
    public boolean releasePicture(String productId,Part filePart, String realPath,String filename) throws IOException;

    /**
     * 分页模糊查找商品
     * @param currentPageStr
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    public ResultInfo findProductByPage(String currentPageStr,String likeProductName,String likeKind,String radio);

    /**
     * 查看商品完整信息
     * @param product
     * @return
     */
    public ResultInfo read(Product product);

}
