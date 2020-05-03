package cjt.dao.impl;

import cjt.dao.FindDao;
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
}
