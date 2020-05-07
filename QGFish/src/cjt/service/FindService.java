package cjt.service;

import cjt.model.Product;
import cjt.model.User;

/**
 * @author cjt
 */
public interface FindService {
    /**
     * 查找用户完整信息
     * @param userId
     * @return
     */
    public User findUser(int userId);

    /**
     * 查找商品完整信息
     * @param productId
     * @return
     */
    public Product findProduct(int productId);

}
