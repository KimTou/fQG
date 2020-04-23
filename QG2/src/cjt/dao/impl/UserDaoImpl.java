package cjt.dao.impl;

import cjt.dao.UserDao;
import cjt.model.ResultInfo;
import cjt.model.User;
import cjt.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cjt
 */
public class UserDaoImpl implements UserDao {
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
                user.setEmail(rs.getString("email"));
                return new ResultInfo(true, "登录成功");
            }
            else {
                return new ResultInfo(false,"用户名或密码错误");
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
        return new ResultInfo(false,"数据库连接错误");
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
            String sql = "insert into user (user_name,password,email) values(?,?,?) ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.execute();
            return new ResultInfo(true, "注册成功");
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new ResultInfo(false,"数据库连接错误");
    }
}
