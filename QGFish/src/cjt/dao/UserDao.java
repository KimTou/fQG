package cjt.dao;

import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

import java.util.List;

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
     * 修改用户密码
     * @param user
     * @return
     */
    public ResultInfo updatePassword(User user);

    /**
     * 用户上传商品图片
     * @param productId
     * @param fileName
     * @return
     */
    public boolean uploadPicture(int productId,String fileName);

    int findProductTotalCount(String likeProductName, String likeKind);

    List<Product> findProductByPage(int start, int rows, String likeProductName, String likeKind,String radio);
}
