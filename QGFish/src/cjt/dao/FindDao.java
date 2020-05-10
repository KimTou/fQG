package cjt.dao;

import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;

import java.util.List;

/**
 * @author cjt
 */
public interface FindDao {
    /**
     * 查找用户信息
     * @param user
     * @return
     */
    public User findUser(User user);

    /**
     * 根据用户输入的数据查找是否有该用户存在
     * @param userName
     * @param email
     * @param phone
     * @return
     */
    public boolean findUser(String userName,String email,String phone);

    /**
     * 根据用户输入的邮箱查找是否有该用户
     * @param email
     * @return
     */
    public User findUser(String email);

    /**
     * 查找商品信息
     * @param product
     * @return
     */
    public Product findProduct(Product product);

    /**
     * 判断用户是否已将该商品加入订单
     * @param productId
     * @param buyer
     * @return
     */
    public boolean findShopping(int productId,int buyer);

    /**
     * 根据订单id查找订单完整信息
     * @param shoppingId
     * @return
     */
    public Shopping findShopping(int shoppingId);

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
    public List<Product> findProductByPage(int start, int rows, String likeProductName, String likeKind, String radio);

}
