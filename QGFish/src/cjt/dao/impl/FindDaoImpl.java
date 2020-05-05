package cjt.dao.impl;

import cjt.dao.FindDao;
import cjt.model.Product;
import cjt.model.User;
import cjt.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cjt
 */
public class FindDaoImpl implements FindDao {
    //连接数据库
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 查找用户完整信息
     * @param user
     * @return
     */
    @Override
    public User findUser(User user) {
        try{
            con= DbUtil.getCon();
            //寻找是否有与输入的用户名密码一致的用户
            String sql="select * from user where id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, user.getUserId());
            rs = stmt.executeQuery();
            //若返回结果集不为空，则填写用户信息到该用户的对象
            if(rs.next()) {
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRealName(rs.getString("real_name"));
                user.setCondition(rs.getString("condi_tion"));
                user.setLabel(rs.getString("label"));
            }
            //封装用户完整信息，以便存入到request域中
            return user;
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

    @Override
    public Product findProduct(Product product) {
        try{
            con= DbUtil.getCon();
            //根据商品id查找商品
            String sql="select * from product where id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, product.getProductId());
            rs = stmt.executeQuery();
            //若返回结果集不为空，则封装商品信息
            if(rs.next()) {
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductKind(rs.getString("product_kind"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setProductAmount(rs.getInt("product_amount"));
                product.setProductSeller(rs.getInt("seller"));
                product.setProductSold(rs.getInt("sold"));
                product.setProductStarLevel(rs.getDouble("star_level"));
                product.setProductComment(rs.getString("comment"));
                product.setProductPicture("/upload/"+rs.getString("picture_path"));
            }
            //将封装完整的product商品对象返回
            return product;
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
