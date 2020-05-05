package cjt.dao;

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
     * 返回所有用户
     * @return
     */
    public ResultInfo findAllUser();

    /**
     * 返回待审核商品
     * @param realPath
     * @return
     */
    public ResultInfo check(String realPath);

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
     * 查询商品总记录数
     * @return
     */
    public int findTotalCount1();

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
}
