package cjt.service;

import cjt.model.Page;
import cjt.model.Product;
import cjt.model.dto.ResultInfo;

/**
 * @author cjt
 */
public interface ManagerService {
    /**
     * 查找所有用户
     * @return
     */
    public ResultInfo findAllUser();

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
    public ResultInfo release(String productId);

    /**
     * 禁止商品发布
     * @param productId
     * @return
     */
    public ResultInfo ban(String productId);

    /**
     * 分页查询
     * @param currentPageStr
     * @return
     */
    public ResultInfo findUserByPage(String currentPageStr);
}
