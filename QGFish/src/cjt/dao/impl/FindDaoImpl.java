package cjt.dao.impl;

import cjt.dao.FindDao;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
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
                user.setAddress(rs.getString("address"));
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
                product.setProductScore(rs.getInt("score"));
                product.setProductScoreTime(rs.getInt("score_time"));
                product.setProductComment(rs.getString("comment"));
                product.setProductPicture("/upload/"+rs.getString("picture_path"));
                product.setProductCondition(rs.getString("condi_tion"));
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

    @Override
    public boolean findShopping(int productId, int buyer) {
        try{
            con= DbUtil.getCon();
            //寻找是否有匹配的购物车信息
            String sql="select * from shopping where product_id=? and buyer=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.setInt(2,buyer);
            rs = stmt.executeQuery();
            //若返回结果集不为空，则填写用户信息到该用户的对象
            if(rs.next()) {
                return true;
            }
            else{
                return false;
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
        return false;
    }

    @Override
    public Shopping findShopping(int shoppingId) {
        try{
            con= DbUtil.getCon();
            Shopping shopping=new Shopping();
            String sql="select * from shopping where id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,shoppingId);
            rs=stmt.executeQuery();
            if(rs.next()){
                shopping.setShoppingId(rs.getInt("id"));
                shopping.setProductId(rs.getInt("product_id"));
                shopping.setProductName(rs.getString("product_name"));
                shopping.setProductKind(rs.getString("product_kind"));
                shopping.setProductPrice(rs.getDouble("product_price"));
                shopping.setProductAmount(rs.getInt("product_amount"));
                shopping.setBuyAmount(rs.getInt("buy_amount"));
                shopping.setTotalPrice(rs.getDouble("total_price"));
                shopping.setSeller(rs.getInt("seller"));
                shopping.setBuyer(rs.getInt("buyer"));
                //从product导出来的picture已带有upload，所以不用再手动加
                shopping.setProductPicture(rs.getString("picture_path"));
                shopping.setProductCondition(rs.getString("condi_tion"));
                shopping.setAddress(rs.getString("address"));
            }
            return shopping;
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
     * 计算商品总数
     * @param likeProductName
     * @param likeKind
     * @return
     */
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

    /**
     * 分页模糊查询商品
     * @param start
     * @param rows
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
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
