package cjt.dao.impl;

import cjt.dao.UserAdvancedDao;
import cjt.model.Appeal;
import cjt.model.Product;
import cjt.model.Shopping;
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
 * 用户高级功能数据（商品，购物车，订单，申诉）
 */
public class UserAdvancedDaoImpl implements UserAdvancedDao {
    /**
     * 连接数据库
     */
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    /**
     * 加入购物车
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo addShopping(Shopping shopping) {
        try{
            con= DbUtil.getCon();
            String sql = "insert into shopping (product_id,product_name,product_kind,product_price,product_amount,seller,buyer,picture_path,condi_tion) value (?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, shopping.getProductId());
            stmt.setString(2, shopping.getProductName());
            stmt.setString(3, shopping.getProductKind());
            stmt.setDouble(4, shopping.getProductPrice());
            stmt.setInt(5,shopping.getProductAmount());
            stmt.setInt(6, shopping.getSeller());
            stmt.setInt(7, shopping.getBuyer());
            stmt.setObject(8,shopping.getProductPicture());
            stmt.setString(9, shopping.getProductCondition());
            stmt.execute();
            return new ResultInfo(true,"添加成功",shopping);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"添加失败",null);
    }

    /**
     * 用户直接购买
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo buy(Shopping shopping) {
        try {
            con = DbUtil.getCon();
            String sql = "insert into shopping (product_id,product_name,product_kind,product_price,product_amount,buy_amount,total_price,seller,buyer,picture_path,condi_tion,address) value (?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, shopping.getProductId());
            stmt.setString(2, shopping.getProductName());
            stmt.setString(3, shopping.getProductKind());
            stmt.setDouble(4, shopping.getProductPrice());
            stmt.setInt(5,shopping.getProductAmount());;
            stmt.setInt(6,shopping.getBuyAmount());;
            stmt.setDouble(7,shopping.getTotalPrice());
            stmt.setInt(8, shopping.getSeller());
            stmt.setInt(9, shopping.getBuyer());
            stmt.setObject(10,shopping.getProductPicture());
            stmt.setString(11, shopping.getProductCondition());
            stmt.setString(12,shopping.getAddress());
            stmt.execute();
            return new ResultInfo(true, "成功提交订单，请等待卖家审核", shopping);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtil.close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"提交订单失败",null);
    }

    /**
     * 用户在购物车里购买
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo buyInShopping(Shopping shopping) {
        try{
            con=DbUtil.getCon();
            String sql="update shopping set buy_amount=?,total_price=?,condi_tion=?,address=? where product_id=? and buyer=?";
            stmt=con.prepareStatement(sql);
            stmt.setInt(1,shopping.getBuyAmount());
            stmt.setDouble(2,shopping.getTotalPrice());
            stmt.setString(3,"等待卖家审核");
            stmt.setString(4,shopping.getAddress());
            //通过商品id和用户id更新购物车信息
            stmt.setInt(5,shopping.getProductId());
            stmt.setInt(6,shopping.getBuyer());
            stmt.executeUpdate();
            return new ResultInfo(true,"成功提交订单，请等待卖家审核",shopping);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"提交订单失败",null);
    }

    /**
     * 增加用户经验值
     * @param user
     */
    @Override
    public void updateUserExp(User user) {
        try{
            con=DbUtil.getCon();
            String sql="update user set exp=? where id=?";
            stmt=con.prepareStatement(sql);
            stmt.setInt(1,user.getExp());
            stmt.setInt(2,user.getUserId());
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * type=1时，查询用户购物车总量
     * type=2时，查询用户商品收到订单的总量
     * type=3时，查询用户订单总量
     * @param userId
     * @param type
     * @return
     */
    @Override
    public int findShoppingTotalCount(int userId,int type) {
        try{
            con= DbUtil.getCon();
            String sql = null;
            if(type==1) {
                sql = "select count(*) from shopping where condi_tion='加入购物车' and buyer=?";
            }
            if(type==2){
                sql="select count(*) from shopping where condi_tion!='加入购物车' and seller=?";
            }
            if(type==3){
                sql="select count(*) from shopping where condi_tion='已发货' and buyer=?";
            }
            stmt = con.prepareStatement(sql);
            //通过用户id寻找该用户购物车总数
            stmt.setInt(1,userId);
            rs=stmt.executeQuery();
            if(rs.next()){
                //返回总记录数
                return rs.getInt(1);
            }
            else {
                return 0;
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
        return 0;
    }

    /**
     * type=1时，分页查询购物车
     * type=2时，分页查询我的商品，即我收到的订单请求
     * type=3时，分页查询我的订单，即卖家已发货的
     * @param start
     * @param rows
     * @param userId
     * @param type
     * @return
     */
    @Override
    public List<Shopping> findShoppingByPage(int start, int rows, int userId, int type) {
        try{
            con= DbUtil.getCon();
            //分页查找所有待审核的商品
            List<Shopping> list=new LinkedList<>();
            String sql=null;
            if(type==1) {
                sql = "select * from shopping where condi_tion='加入购物车' and buyer=";
            }
            if(type==2){
                sql = "select * from shopping where condi_tion!='加入购物车' and seller=";
            }
            if(type==3){
                sql="select * from shopping where condi_tion='已发货' and buyer=";
            }
            StringBuilder sb=new StringBuilder(sql);
            //添加查询条件
            sb.append(userId);
            //添加分页参数
            sb.append(" limit "+start+","+rows);
            stmt = con.prepareStatement(sb.toString());
            rs=stmt.executeQuery();
            while(rs.next()){
                Shopping shopping=new Shopping();
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
                list.add(shopping);
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
     * 用户从购物车在删除该商品
     * 卖家拒绝订单
     * 买家取消订单
     * 买家确认收货
     * @param shoppingId
     * @return
     */
    @Override
    public ResultInfo deleteInShopping(int shoppingId) {
        try{
            con= DbUtil.getCon();
            String sql="delete from shopping where id=?";
            stmt = con.prepareStatement(sql);
            //通过编号定位
            stmt.setInt(1,shoppingId);
            stmt.execute();
            return new ResultInfo(true,"删除成功",shoppingId);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"删除失败",null);
    }

    /**
     * 卖家确认订单，确认发货
     * @param shoppingId
     * @return
     */
    @Override
    public ResultInfo allowBuy(int shoppingId) {
        try{
            con= DbUtil.getCon();
            String sql="update shopping set condi_tion=? where id=?";
            stmt = con.prepareStatement(sql);
            //通过编号定位
            stmt.setString(1,"已发货");
            stmt.setInt(2,shoppingId);
            stmt.execute();
            return new ResultInfo(true,"发货成功",shoppingId);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"发货失败",null);
    }

    /**
     * 评价商品并更新商品信息
     * @param product
     * @return
     */
    @Override
    public ResultInfo evaluate(Product product) {
        try{
            con= DbUtil.getCon();
            String sql="update product set product_amount=?,sold=?,star_level=?,score=?,score_time=?,comment=? where id=?";
            stmt = con.prepareStatement(sql);
            //更新商品信息
            stmt.setInt(1,product.getProductAmount());
            stmt.setInt(2,product.getProductSold());
            stmt.setDouble(3,product.getProductStarLevel());
            stmt.setInt(4,product.getProductScore());
            stmt.setInt(5,product.getProductScoreTime());
            stmt.setString(6,product.getProductComment());
            //通过编号定位
            stmt.setInt(7,product.getProductId());
            stmt.execute();
            return new ResultInfo(true,"操作完成",product);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"操作失败",null);
    }

    /**
     * 卖家查询自己发布的商品的总量
     * @param userId
     * @return
     */
    @Override
    public int findMyProductTotalCount(int userId) {
        try{
            con= DbUtil.getCon();
            //定义初始化sql模板
            String sql="select count(*) from product where condi_tion='允许发布' and seller=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,userId);
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
     * 卖家分页查询自己发布的商品
     * @param start
     * @param rows
     * @param userId
     * @return
     */
    @Override
    public List<Product> findMyProduct(int start, int rows, int userId) {
        try{
            con= DbUtil.getCon();
            //分页查找所有待审核的商品
            List<Product> list=new LinkedList<>();
            String sql="select * from product where condi_tion='允许发布' and seller=";
            StringBuilder sb=new StringBuilder(sql);
            sb.append(userId);
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

    /**
     * 卖家回复自己商品的评论
     * @param product
     * @return
     */
    @Override
    public ResultInfo reply(Product product) {
        try{
            con= DbUtil.getCon();
            String sql="update product set comment=? where id=?";
            stmt = con.prepareStatement(sql);
            //更新商品信息
            stmt.setString(1,product.getProductComment());
            //通过编号定位
            stmt.setInt(2,product.getProductId());
            stmt.executeUpdate();
            return new ResultInfo(true,"回复成功",product);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"回复失败",null);
    }

    /**
     * 卖家修改订单
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo updateShopping(Shopping shopping) {
        try{
            con=DbUtil.getCon();
            String sql="update shopping set product_name=?,product_kind=?,product_price=?,buy_amount=?,total_price=?,address=? where id=?";
            stmt=con.prepareStatement(sql);
            stmt.setString(1,shopping.getProductName());
            stmt.setString(2,shopping.getProductKind());
            stmt.setDouble(3,shopping.getProductPrice());
            stmt.setInt(4,shopping.getBuyAmount());
            stmt.setDouble(5,shopping.getTotalPrice());
            stmt.setString(6,shopping.getAddress());
            //根据订单id定位修改
            stmt.setInt(7,shopping.getShoppingId());
            stmt.executeUpdate();
            return new ResultInfo(true,"订单修改成功",shopping);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"订单修改失败",null);
    }

    @Override
    public ResultInfo appeal(Appeal appeal) {
        try{
            con= DbUtil.getCon();
            String sql = "insert into appeal (user_id,appeal_title,appeal_content) value (?,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,appeal.getUserId());
            stmt.setString(2,appeal.getAppealTitle());
            stmt.setString(3,appeal.getAppealContent());
            stmt.execute();
            return new ResultInfo(true,"提交成功",appeal);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"提交失败",null);
    }

}
