package cjt.service;

import cjt.model.Appeal;
import cjt.model.Shopping;
import cjt.model.dto.ResultInfo;

import java.io.IOException;

/**
 * @author cjt
 * 用户高级功能业务（商品，购物车，订单，申诉）
 */
public interface UserAdvancedService {

    /**
     * 添加商品进购物车
     * @param shopping
     * @return
     */
    public ResultInfo addShopping(Shopping shopping);

    /**
     * 用户直接购买商品
     * @param shopping
     * @return
     */
    public ResultInfo buy(Shopping shopping);

    /**
     * 用户在购物车内提交购买信息
     * @param shopping
     * @return
     */
    public ResultInfo buyInShopping(Shopping shopping);

    /**
     * type=1时，分页查询购物车
     * type=2时，分页查询我的商品，即我收到的订单请求
     * @param currentPage
     * @param userId
     * @param type
     * @return
     */
    public ResultInfo findShoppingByPage(int currentPage,int userId,int type);

    /**
     * 用户从购物车中删除该商品
     * 卖家拒绝订单
     * @param shoppingId
     * @return
     */
    public ResultInfo deleteInShopping(int shoppingId);

    /**
     * 卖家确认订单，确认发货
     * @param shoppingId
     * @return
     */
    public ResultInfo allowBuy(int shoppingId);

    /**
     * 订单文件下载
     * @param shoppingIdStr
     * @return
     * @throws IOException
     */
    public String downLoad(String shoppingIdStr) throws IOException;

    /**
     * 评价订单商品
     * @param shoppingId
     * @param score
     * @param comment
     * @return
     */
    public ResultInfo evaluate(int shoppingId,int score,String comment);

    /**
     * 卖家查询自己上架的商品
     * @param currentPage
     * @param userId
     * @return
     */
    public ResultInfo findMyProduct(int currentPage,int userId);

    /**
     * 卖家回复自己商品的评论
     * @param comment
     * @param productId
     * @return
     */
    public ResultInfo reply(String comment,int productId);

    /**
     * 修改订单信息
     * @param shopping
     * @return
     */
    public ResultInfo updateShopping(Shopping shopping);

    /**
     * 用户提交申诉信息
     * @param appeal
     * @return
     */
    public ResultInfo appeal(Appeal appeal);
}
