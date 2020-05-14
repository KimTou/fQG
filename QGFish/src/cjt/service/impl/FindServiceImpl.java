package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.service.FindService;

/**
 * @author cjt
 */
public class FindServiceImpl implements FindService {
    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @Override
    public User findUser(int userId) {
        if(userId>0) {
            User user = new User();
            user.setUserId(userId);
            FindDao findDao = new FindDaoImpl();
            //通过数据库查询用户完整信息
            return findDao.findUser(user);
        }else {
            return null;
        }
    }

    /**
     * 根据商品id查询商品信息
     * @param productId
     * @return
     */
    @Override
    public Product findProduct(int productId) {
        if(productId>0) {
            Product product = new Product();
            product.setProductId(productId);
            FindDao findDao = new FindDaoImpl();
            //通过数据库查询商品完整信息
            return findDao.findProduct(product);
        }else{
            return null;
        }
    }

    /**
     * 根据订单id查询订单信息
     * @param shoppingId
     * @return
     */
    @Override
    public Shopping findShopping(int shoppingId) {
        if(shoppingId>0) {
            FindDao findDao = new FindDaoImpl();
            return findDao.findShopping(shoppingId);
        }else{
            return null;
        }
    }


}
