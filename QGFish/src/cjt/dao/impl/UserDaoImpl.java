package cjt.dao.impl;

import cjt.dao.UserDao;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cjt
 */
public class UserDaoImpl implements UserDao {
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
                user.setRealName(rs.getString("real_name"));
                user.setCondition(rs.getString("condi_tion"));
                user.setLabel(rs.getString("label"));
                return new ResultInfo(true, "登录成功",user);
                //封装用户完整信息，以便存入到session域中
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
            String sql = "insert into user (user_name,password,email,phone,real_name,condi_tion) values(?,?,?,?,?,?) ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getRealName());
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
            String sql = "insert into product (product_name,product_kind,product_price,product_amount,sold,star_level,score,score_time,seller,condi_tion) values(?,?,?,?,?,?,?,?,?,?)  ";
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
            stmt.setObject(10, product.getProductCondition());
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
            String sql = "update user set user_name=?,email=?,phone=?,real_name=? where id=?";
            stmt = con.prepareStatement(sql);
            //改用户名
            stmt.setString(1, user.getUserName());
            //改邮箱
            stmt.setString(2, user.getEmail());
            //改电话
            stmt.setString(3, user.getPhone());
            //改姓名
            stmt.setString(4, user.getRealName());
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

    @Override
    public int findProductTotalCount(String likeProductName, String likeKind) {
        try{
            con= DbUtil.getCon();
            //定义初始化sql模板
            String sql="select count(*) from product where condi_tion='允许发布' ";
            StringBuilder sb=new StringBuilder(sql);
            //如果条件不为空，而添加模糊条件
            if(likeProductName!=null&&!"".equals(likeProductName)){
                sb.append(" and product_name like '%"+likeProductName+"%'");
            }
            if(likeKind!=null&&!"".equals(likeKind)){
                sb.append(" and product_kind like '%"+likeKind+"%'");
            }
            stmt = con.prepareStatement(sb.toString());
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

    @Override
    public List<Product> findProductByPage(int start, int rows, String likeProductName, String likeKind,String radio) {
        try{
            con= DbUtil.getCon();
            //分页查找所有待审核的商品
            List<Product> list=new LinkedList<>();
            String sql="select * from product where condi_tion='允许发布' ";
            StringBuilder sb=new StringBuilder(sql);
            //如果条件不为空，而添加模糊条件
            if(likeProductName!=null&&!"".equals(likeProductName)){
                sb.append(" and product_name like '%"+likeProductName+"%'");
            }
            if(likeKind!=null&&!"".equals(likeKind)){
                sb.append(" and product_kind like '%"+likeKind+"%'");
            }
            //添加排序条件，倒序排序
            sb.append(" order by "+radio+" desc");
            //添加分页参数
            sb.append(" limit "+start+","+rows);
            stmt = con.prepareStatement(sb.toString());
            System.out.println(sb.toString());
            rs=stmt.executeQuery();
            while(rs.next()){
                Product product=new Product();
                //封装每一个商品对象
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductKind(rs.getString("product_kind"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductAmount(rs.getInt("product_amount"));
                product.setProductSold(rs.getInt("sold"));
                product.setProductStarLevel(rs.getDouble("star_level"));
                product.setProductComment(rs.getString("comment"));
                product.setProductPicture("/upload/"+rs.getString("picture_path"));
                list.add(product);
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


}
