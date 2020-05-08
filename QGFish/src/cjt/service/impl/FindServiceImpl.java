package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;

import java.util.List;

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

    @Override
    public Shopping findShopping(int shoppingId) {
        FindDao findDao=new FindDaoImpl();
        return findDao.findShopping(shoppingId);
    }

    /**
     * 用户主界面分页模糊查询所有商品
     * @param currentPage
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    @Override
    public ResultInfo findProductByPage(int currentPage,String likeProductName,String likeKind,String radio) {
        Page<Product> page=new Page<>();
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        FindDao findDao=new FindDaoImpl();
        int totalCount=findDao.findProductTotalCount(likeProductName,likeKind);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list=findDao.findProductByPage(start,rows,likeProductName,likeKind,radio);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }


}
