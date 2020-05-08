package cjt.dao;

import cjt.model.Appeal;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;

import java.util.List;


/**
 * @author cjt
 */
public interface ManagerDao {

    /**
     * 查询待审核商品总记录数
     * @return
     */
    public int findProductTotalCount();

    /**
     * 分页查询所有待审核商品
     * @param start
     * @param rows
     * @return
     */
    public List<Product> check(int start,int rows);

    /**
     * 允许商品发布
     * @param productId
     * @return
     */
    public ResultInfo release(int productId);

    /**
     * 禁止商品发布
     * @param productId
     * @return
     */
    public ResultInfo ban(int productId);


    /**
     * 查询用户总记录数
     * @return
     */
    public int findUserTotalCount();

    /**
     * 分页查询商品集合
     * @param start
     * @param rows
     * @return
     */
    public List<User> findUserByPage(int start, int rows);

    /**
     * 恢复用户售卖
     * @param userId
     * @return
     */
    public ResultInfo recover(int userId);

    /**
     * 禁止用户售卖
     * @param userId
     * @param label
     * @return
     */
    public ResultInfo banUser(int userId,String label);

    /**
     * 获取申诉信息总数
     * @return
     */
    public int findTotalAppeal();

    /**
     * 分页获取申诉信息
     * @param start
     * @param rows
     * @return
     */
    public List<Appeal> getAppeal(int start, int rows);

    /**
     * 对申诉信息标记已读
     * @param id
     * @return
     */
    public ResultInfo read(int id);
}
