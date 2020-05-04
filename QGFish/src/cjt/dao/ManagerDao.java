package cjt.dao;

import cjt.model.dto.ResultInfo;


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
}
