package cjt.service.impl;

import cjt.dao.ManagerDao;
import cjt.dao.impl.ManagerDaoImlp;
import cjt.model.dto.ResultInfo;
import cjt.service.ManagerService;


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
}
