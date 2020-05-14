package cjt.dao;

import cjt.model.Appeal;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

import java.util.List;

/**
 * @author cjt
 * 用户基础功能与数据库交互的接口
 */
public interface UserBaseDao {
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

    /**
     * 寻找用户喜欢的商品种类
     * @param userId
     * @return
     */
    public List<String> findUserLoveKind(int userId);

    /**
     * 根据用户喜欢的商品种类查找商品
     * @param productKind
     * @return
     */
    public List<Product> findUserLove(String productKind);

    /**
     * 为用户推荐最便宜实惠的
     * @return
     */
    public List<Product> findCheapProduct();

}
