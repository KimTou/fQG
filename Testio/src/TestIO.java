import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class TestIO {
    public static void main(String[] args) throws IOException {
        Connection con = null;                        //为了关闭时不用再创建对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/connectio" +    //选择合适的数据库
                    "?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
            int cz = 1;
            while (cz != 2) {
                System.out.println("请输入你想使用的文件：(包括目录和文件名)");
                String fileName = sc.nextLine();
                File file = new File(fileName);
                File dir = file.getParentFile();
                if (dir.exists() && file.exists()) {                 //若都存在，则不用新建
                    System.out.println("已查找到该文件");
                }
                if (!dir.exists()) {                     //若目录不存在，则新建目录和文件
                    dir.mkdirs();
                    try {
                        System.out.println("文件路径不存在，已为你新建好");
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (dir.exists() && !file.exists()) {           //若目录存在文件不存在，则新建文件
                    try {
                        System.out.println("文件不存在，已为你新建好");
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//        FileWriter w=new FileWriter(fileName);
//        w.write("你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁你是谁\n"+
//                "我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人我是好人\n"+
//                "一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗一起战斗\n"+
//                "拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天拥抱明天\n");
//        w.close();
                System.out.println("请输入你想从第几行开始更新：");
                int h = sc.nextInt();                             //读取行数
                while (h < 1 || h > 1000) {
                    System.out.println("请输入合适的行数：");
                    h = sc.nextInt();                            //处理用户的不合理操作
                }
                System.out.println("请输入你想更新的内容：");
                sc.nextLine();                                //去掉回车键的缓存
                String content = sc.nextLine();                 //读取内容
                ByteBuffer bb = ByteBuffer.wrap((content).getBytes(StandardCharsets.UTF_8));
                RandomAccessFile ra = new RandomAccessFile(file, "rw");
                int pos = 1;
                do {
                    if (pos == h) {
                        FileChannel fc = ra.getChannel();         //获得通道
                        fc.write(bb);
                        break;
                    } else {
                        pos++;
                    }
                } while (ra.readLine() != null);
                if (pos != h) {
                    FileChannel fc = ra.getChannel();
                    /*while (pos != h) {                       //若超过原有行数，则在加换行符，直到目标行数则写入
                        fc.write(ByteBuffer.wrap("\n".getBytes(StandardCharsets.UTF_8)));
                        pos++;
                    }*/
                    System.out.println("超过原有行数，已在文件末尾补加并换行");
                    fc.write(bb);
                    fc.write(ByteBuffer.wrap("\n".getBytes(StandardCharsets.UTF_8)));
                }
                System.out.println("更新成功！");
                ra.close();                       //关闭流
                //开始数据库处理
                String sql = "insert into io (content,time) values(?,?)";                  //表的名字是io
                ps = con.prepareStatement(sql);
                ps.setString(1, content);
                ps.setObject(2, new Timestamp(System.currentTimeMillis()));
                ps.execute();
                System.out.println("成功写入数据库");
                System.out.println("是否需要查看历史输入数据：(1:是  2:否)");
                cz = sc.nextInt();
                while (cz < 1 && cz > 2) {
                    System.out.println("请输入正确数字！");
                    cz = sc.nextInt();
                }
                if (cz == 1) {
                    sql = "select * from io order by time ";
                    ps = con.prepareStatement(sql);
                    ps.execute();                   //执行
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + "---" + rs.getString(2) + "\n" + "时间：" +
                                rs.getString(3) + '\n' + "来自文件:" + file.getPath());
                    }
                }
                System.out.println("是否继续：(1.是  2.否）");
                cz = sc.nextInt();
                while (cz < 1 && cz > 2) {
                    System.out.println("请输入正确数字！");
                    cz = sc.nextInt();
                }
                sc.nextLine();         //去掉缓冲区的回测
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
                try {                                       //关闭接口对象
                    if (rs != null)                           //要按照ResultSet->Statement->Connection的顺序，并且分开写
                        rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (ps != null)
                        ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

