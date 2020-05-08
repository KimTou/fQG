package cjt.service;

import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

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

    /**
     * 查找订单完整信息
     * @param shoppingId
     * @return
     */
    public Shopping findShopping(int shoppingId);

    /**
     * 分页模糊查找商品
     * @param currentPage
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    public ResultInfo findProductByPage(int currentPage, String likeProductName, String likeKind, String radio);

}
