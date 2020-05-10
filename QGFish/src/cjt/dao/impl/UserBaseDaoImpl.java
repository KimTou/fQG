package cjt.dao.impl;

import cjt.dao.UserBaseDao;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.util.DbUtil;

import java.sql.*;

/**
 * @author cjt
 * 用户基础数据（个人信息，商品信息）
 */
public class UserBaseDaoImpl implements UserBaseDao {
    //连接数据库
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public ResultInfo login(User user){
        try{
            con= DbUtil.getCon();
            //寻找是否有与输入的用户名密码一致的用户
            String sql="select * from user where user_name=? and password=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            rs = stmt.executeQuery();
            //若返回结果集不为空，则填写用户信息到该用户的对象
            if(rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setCondition(rs.getString("condi_tion"));
                user.setLabel(rs.getString("label"));
                return new ResultInfo(true, "登录成功",user);
                //封装用户完整信息
            }
            else {
                return new ResultInfo(false,"用户名或密码错误",null);
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        //不要忘了在WEB-INF下导入jar包
        return new ResultInfo(false,"数据库连接错误",null);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResultInfo register(User user){
        try{
            con= DbUtil.getCon();
            //存入用户输入的信息
            String sql = "insert into user (user_name,password,email,phone,address,condi_tion) values(?,?,?,?,?,?) ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getCondition());
            stmt.execute();
            return new ResultInfo(true, "注册成功",user);
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
     * 卖家上传商品
     * @param product
     * @return
     */
    @Override
    public ResultInfo release(Product product) {
        try{
            con= DbUtil.getCon();
            //存入用户输入的商品信息
            String sql = "insert into product (product_name,product_kind,product_price,product_amount,sold,star_level,score,score_time,seller,comment,condi_tion) values(?,?,?,?,?,?,?,?,?,?,?)  ";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductKind());
            stmt.setObject(3, product.getProductPrice());
            stmt.setObject(4, product.getProductAmount());
            stmt.setObject(5, product.getProductSold());
            stmt.setObject(6, product.getProductStarLevel());
            stmt.setObject(7, product.getProductScore());
            stmt.setObject(8, product.getProductScoreTime());
            stmt.setObject(9, product.getProductSeller());
            stmt.setString(10, product.getProductComment());
            stmt.setObject(11, product.getProductCondition());
            stmt.executeUpdate();
            rs=stmt.getGeneratedKeys();
            if(rs.next()){
                //获得插入后生成的主键id
                product.setProductId(rs.getInt(1));
            }
            return new ResultInfo(true, "商品上传成功，请等待管理员审核",product);
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
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public ResultInfo update(User user) {
        try{
            con= DbUtil.getCon();
            String sql = "update user set user_name=?,email=?,phone=?,address=? where id=?";
            stmt = con.prepareStatement(sql);
            //改用户名
            stmt.setString(1, user.getUserName());
            //改邮箱
            stmt.setString(2, user.getEmail());
            //改电话
            stmt.setString(3, user.getPhone());
            //改姓名
            stmt.setString(4, user.getAddress());
            //根据用户id定位
            stmt.setInt(5, user.getUserId());
            stmt.executeUpdate();
            return new ResultInfo(true,"保存成功",user);
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
     * 更新用户密码
     * 用户忘记密码后重置密码
     * @param user
     * @return
     */
    @Override
    public ResultInfo updatePassword(User user) {
        try{
            con= DbUtil.getCon();
            String sql = "update user set password=? where id=?";
            stmt = con.prepareStatement(sql);
            //修改用户密码
            stmt.setString(1, user.getPassword());
            //根据用户id定位
            stmt.setInt(2, user.getUserId());
            stmt.executeUpdate();
            return new ResultInfo(true,"密码修改成功",user);
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
     * 为指定商品添加图片名
     * @param productId
     * @param fileName
     * @return
     */
    @Override
    public boolean uploadPicture(int productId, String fileName) {
        try{
            con= DbUtil.getCon();
            String sql = "update product set picture_path=? where id=?";
            stmt = con.prepareStatement(sql);
            //添加图片名
            stmt.setString(1, fileName);
            //根据商品id定位
            stmt.setInt(2, productId);
            stmt.executeUpdate();
            return true;
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }




}
