package cjt.dao;

import cjt.model.Product;
import cjt.model.Shopping;
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

    /**
     * 模糊查询的商品总量
     * @param likeProductName
     * @param likeKind
     * @return
     */
    public int findProductTotalCount(String likeProductName, String likeKind);

    /**
     * 分页模糊查询商品
     * @param start
     * @param rows
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    public List<Product> findProductByPage(int start, int rows, String likeProductName, String likeKind,String radio);

    /**
     * 添加商品进购物车
     * @param shopping
     * @return
     */
    public ResultInfo addShopping(Shopping shopping);

    /**
     * 用户直接购买商品
     * @param shopping
     * @return
     */
    public ResultInfo buy(Shopping shopping);

    /**
     * 用户在购物车内购买商品
     * @param shopping
     * @return
     */
    public ResultInfo buyInShopping(Shopping shopping);

    /**
     * 查询用户购物车总量
     * 查询用户商品收到订单的总量
     * 查询用户订单总量
     * @param userId
     * @param type
     * @return
     */
    public int findShoppingTotalCount(int userId,int type);

    /**
     * type=1时，分页查询购物车
     * type=2时，分页查询我的商品，即我收到的订单请求
     * @param start
     * @param rows
     * @param userId
     * @param type
     * @return
     */
    public List<Shopping> findShoppingByPage(int start, int rows,int userId,int type);

    /**
     * 用户从购物车中删除该商品
     * 卖家拒绝订单
     * @param shoppingId
     * @return
     */
    public ResultInfo deleteInShopping(int shoppingId);

    /**
     * 卖家确认订单，确认发货
     * @param shoppingId
     * @return
     */
    public ResultInfo allowBuy(int shoppingId);



}
