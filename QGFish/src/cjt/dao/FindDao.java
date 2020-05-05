package cjt.dao;

import cjt.model.Product;
import cjt.model.User;

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
     * 查找商品信息
     * @param product
     * @return
     */
    public Product findProduct(Product product);
}
