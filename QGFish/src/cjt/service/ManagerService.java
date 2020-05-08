package cjt.service;

import cjt.model.Page;
import cjt.model.Product;
import cjt.model.dto.ResultInfo;

/**
 * @author cjt
 */
public interface ManagerService {

    /**
     * 查找所有待审核的商品
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
     * 分页查询
     * @param currentPage
     * @return
     */
    public ResultInfo findUserByPage(int currentPage);
}
