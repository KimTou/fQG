package cjt.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author cjt
 */
@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width=100;
        int height=50;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //获取画笔
        Graphics g=image.getGraphics();
        g.setColor(Color.GRAY);
        //填充背景颜色
        g.fillRect(0,0,width,height);
        g.setColor(Color.blue);
        //画边框
        g.drawRect(0,0,width-1,height-1);
        g.setColor(Color.YELLOW);
        //设置字体大小
        g.setFont(new Font("黑体",Font.BOLD,24));
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        //生成随机角标
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<=4;i++){
            int index=random.nextInt(str.length());
            //获取随机字符
            char ch=str.charAt(index);
            sb.append(ch);
            //写验证码
            g.drawString(ch+"",width/5*i,height/2);
        }
        String checkCode_session=sb.toString();
        //将验证码存入session
        request.getSession().setAttribute("checkCode_session",checkCode_session);
        //画干扰线
        g.setColor(Color.GREEN);
        //随机生成坐标点
        for (int i = 0; i < 7; i++) {
            int x1=random.nextInt(width);
            int x2=random.nextInt(width);
            int y1=random.nextInt(height);
            int y2=random.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //将图片展示到页面上
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

}
