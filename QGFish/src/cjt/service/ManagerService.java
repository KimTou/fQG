package cjt.service;

import cjt.model.Page;
import cjt.model.Product;
import cjt.model.dto.ResultInfo;

/**
 * @author cjt
 */
public interface ManagerService {

    /**
     * 分页返回所有待审核的商品
     * @param currentPage
     * @return
     */
    public ResultInfo check(int currentPage);

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
     * 分页查询
     * @param currentPage
     * @return
     */
    public ResultInfo findUserByPage(int currentPage);

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
     * 获取申诉信息
     * @param currentPage
     * @return
     */
    public ResultInfo getAppeal(int currentPage);

    /**
     * 对申诉信息标记已读
     * @param id
     * @return
     */
    public ResultInfo read(int id);
}
