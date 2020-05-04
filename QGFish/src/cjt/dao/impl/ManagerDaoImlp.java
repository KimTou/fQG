package cjt.dao.impl;

import cjt.dao.ManagerDao;
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
    //连接数据库
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 返回所有用户
     * @return
     */
    @Override
    public ResultInfo findAllUser() {
        try{
            List<User> list=new LinkedList<>();
            con= DbUtil.getCon();
            //寻找是否有与输入的用户名密码一致的用户
            String sql="select * from user where user_name!=? ";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1);
            rs = stmt.executeQuery();
            //若返回结果集不为空，则填写用户信息到该用户的对象
            while(rs.next()) {
                User user =new User();
                //封装每一个用户
                user.setUserId(rs.getInt("id"));
                user.setEmail(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRealName(rs.getString("real_name"));
                user.setCondition(rs.getString("condi_tion"));
                //把每个用户添加到列表中
                list.add(user);
            }
            return new ResultInfo(true,"查找成功",list);
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

    @Override
    public ResultInfo check(String realPath) {
        try{
            List<Product> list=new LinkedList<>();
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="select * from product where condi_tion=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"待审核" );
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
                System.out.println(product.getProductPicture());
                //把每个商品添加到列表中
                list.add(product);
            }
            return new ResultInfo(true,"查找成功",list);
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

    @Override
    public ResultInfo ban(int productId) {
        try{
            con= DbUtil.getCon();
            //查找所有待审核的商品
            String sql="update product set condi_tion=? where id=? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1,"禁止发布");
            stmt.setInt(2,productId);
            stmt.executeUpdate();
            return new ResultInfo(true,"已禁止发布",null);
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
