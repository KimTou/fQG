package cjt.service.impl;

import cjt.dao.ManagerDao;
import cjt.dao.UserDao;
import cjt.dao.impl.ManagerDaoImlp;
import cjt.dao.impl.UserDaoImpl;
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
     * 返回所有用户
     * @return
     */
    @Override
    public ResultInfo findAllUser() {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.findAllUser();
    }

    @Override
    public ResultInfo check(String realPath) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.check(realPath);
    }

    @Override
    public ResultInfo release(String productId) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.release(Integer.parseInt(productId));
    }

    @Override
    public ResultInfo ban(String productId) {
        ManagerDao managerDao=new ManagerDaoImlp();
        return managerDao.ban(Integer.parseInt(productId));
    }

    @Override
    public ResultInfo findUserByPage(String currentPageStr) {
        Page<User> page=new Page<>();
        int currentPage=Integer.parseInt(currentPageStr);
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
        System.out.println(page);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }
}
