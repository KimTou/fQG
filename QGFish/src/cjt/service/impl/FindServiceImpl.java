package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.Product;
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
        User user =new User();
        user.setUserId(userId);
        FindDao findDao = new FindDaoImpl();
        //通过数据库查询用户完整信息
        return findDao.findUser(user);
    }

    @Override
    public Product findProduct(int productId) {
        Product product=new Product();
        product.setProductId(productId);
        FindDao findDao=new FindDaoImpl();
        //通过数据库查询商品完整信息
        return findDao.findProduct(product);
    }

}
