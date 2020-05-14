package cjt.dao.impl;

import cjt.dao.ManagerDao;
import cjt.model.Appeal;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cjt
 */
public class ManagerDaoImlp implements ManagerDao {
    /**
     * 连接数据库
     */
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 计算待审核商品总数
     * @return
     */
    @Override
    public int findProductTotalCount() {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="select count(*) from product where condi_tion=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"待审核");
            rs=stmt.executeQuery();
            if(rs.next()){
                //返回总记录数
                return rs.getInt(1);
            }
            return 0;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 分页查询所有待审核的商品
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<Product> check(int start,int rows) {
        try{
            List<Product> list=new LinkedList<>();
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="select * from product where condi_tion='待审核' limit ?,?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,start);
            stmt.setInt(2,rows);
            rs=stmt.executeQuery();
            while(rs.next()) {
                Product product=new Product();
                //封装每一个商品
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductKind(rs.getString("product_kind"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductAmount(rs.getInt("product_amount"));
                product.setProductSeller(rs.getInt("seller"));
                //路径为service.xml中设置的路径
                product.setProductPicture("/upload/"+rs.getString("picture_path"));
                //把每个商品添加到列表中
                list.add(product);
            }
            return list;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 允许商品发布
     * @param productId
     * @return
     */
    @Override
    public ResultInfo release(int productId) {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="update product set condi_tion=? where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"允许发布");
            stmt.setInt(2,productId);
            stmt.executeUpdate();
            return new ResultInfo(true,"发布成功",null);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误",null);
    }

    /**
     * 禁止商品发布
     * 商品下架
     * @param productId
     * @return
     */
    @Override
    public ResultInfo ban(int productId) {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="delete from product where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,productId);
            stmt.executeUpdate();
            return new ResultInfo(true,"删除成功",null);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误",null);
    }


    /**
     * 计算用户总量
     * @return
     */
    @Override
    public int findUserTotalCount() {
        try{
            con= DbUtil.getCon();
            //查找所有除管理员自己的用户
            String sql="select count(*) from user where id!=1";
            stmt = con.prepareStatement(sql);
            rs=stmt.executeQuery();
            if(rs.next()){
                //返回总记录数
                return rs.getInt(1);
            }
            return 0;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 分页查询用户
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<User> findUserByPage(int start, int rows) {
        try{
            con= DbUtil.getCon();
            //分页查找除管理员自己的所有用户
            List<User> list=new LinkedList<>();
            String sql="select * from user where id!=1 limit ?,?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,start);
            stmt.setInt(2,rows);
            rs=stmt.executeQuery();
            while(rs.next()){
                User user=new User();
                //封装每一个用户对象
                user.setUserId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setCondition(rs.getString("condi_tion"));
                list.add(user);
                //返回总记录数
            }
            return list;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 恢复用户售卖
     * @param userId
     * @return
     */
    @Override
    public ResultInfo recover(int userId) {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="update user set condi_tion=?,label=? where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"正常");
            stmt.setString(2,null);
            stmt.setInt(3,userId);
            stmt.executeUpdate();
            return new ResultInfo(true,"恢复成功",null);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误",null);
    }

    /**
     * 禁止用户售卖
     * @param userId
     * @param label
     * @return
     */
    @Override
    public ResultInfo banUser(int userId, String label) {
        try{
            con= DbUtil.getCon();
            //更改状态，并贴上标签理由
            String sql="update user set condi_tion=?,label=? where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"禁止售卖");
            stmt.setString(2,label);
            stmt.setInt(3,userId);
            stmt.executeUpdate();
            return new ResultInfo(true,"禁售成功",null);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误",null);
    }

    /**
     * 查询申诉信息总数
     * @return
     */
    @Override
    public int findTotalAppeal() {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="select count(*) from appeal ";
            stmt = con.prepareStatement(sql);
            rs=stmt.executeQuery();
            if(rs.next()){
                //返回总记录数
                return rs.getInt(1);
            }
            return 0;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 分页获取所有待处理的申申诉信息
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<Appeal> getAppeal(int start, int rows) {
        try{
            con= DbUtil.getCon();
            List<Appeal> list=new LinkedList<>();
            String sql="select * from appeal limit ?,?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,start);
            stmt.setInt(2,rows);
            rs=stmt.executeQuery();
            while(rs.next()){
                Appeal appeal=new Appeal();
                appeal.setId(rs.getInt("id"));
                appeal.setUserId(rs.getInt("user_id"));
                appeal.setAppealTitle(rs.getString("appeal_title"));
                appeal.setAppealContent(rs.getString("appeal_content"));
                list.add(appeal);
            }
            //返回存有每页的申诉信息
            return list;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 对申诉信息标记已读
     * @param id
     * @return
     */
    @Override
    public ResultInfo read(int id) {
        try{
            con= DbUtil.getCon();
            //更改状态，并贴上标签理由
            String sql="delete from appeal where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,id);
            stmt.execute();
            return new ResultInfo(true,"操作成功",null);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误",null);
    }


}
