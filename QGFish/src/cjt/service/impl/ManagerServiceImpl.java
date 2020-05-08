package cjt.service.impl;

import cjt.dao.ManagerDao;
import cjt.dao.impl.ManagerDaoImlp;
import cjt.model.Appeal;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.ManagerService;

import java.util.List;

/**
 * @author cjt
 */
public class ManagerServiceImpl implements ManagerService {

    /**
     * 分页返回所有待审核的商品
     * @param currentPage
     * @return
     */
    @Override
    public ResultInfo check(int currentPage) {
        Page<Product> page=new Page<>();
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        ManagerDao managerDao=new ManagerDaoImlp();
        int totalCount=managerDao.findProductTotalCount();
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list=managerDao.check(start,rows);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 允许发布该商品
     * @param productId
     * @return
     */
    @Override
    public ResultInfo release(int productId) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.release(productId);
    }

    /**
     * 禁止发布该商品
     * @param productId
     * @return
     */
    @Override
    public ResultInfo ban(int productId) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.ban(productId);
    }

    /**
     * 分页查询所有用户
     * @param currentPage
     * @return
     */
    @Override
    public ResultInfo findUserByPage(int currentPage) {
        Page<User> page=new Page<>();
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询总记录数
        ManagerDao managerDao=new ManagerDaoImlp();
        int totalCount=managerDao.findUserTotalCount();
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*page.getRows();
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<User> list=managerDao.findUserByPage(start,rows);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 恢复用户售卖
     * @param userId
     * @return
     */
    @Override
    public ResultInfo recover(int userId) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.recover(userId);
    }

    /**
     * 禁止用户售卖
     * @param userId
     * @param label
     * @return
     */
    @Override
    public ResultInfo banUser(int userId, String label) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.banUser(userId,label);
    }

    /**
     * 分页获取申诉信息
     * @param currentPage
     * @return
     */
    @Override
    public ResultInfo getAppeal(int currentPage) {
        Page<Appeal> page=new Page<>();
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        ManagerDao managerDao=new ManagerDaoImlp();
        int totalCount=managerDao.findTotalAppeal();
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Appeal> list=managerDao.getAppeal(start,rows);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 对申诉信息标记已读
     * @param id
     * @return
     */
    @Override
    public ResultInfo read(int id) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.read(id);
    }
}
