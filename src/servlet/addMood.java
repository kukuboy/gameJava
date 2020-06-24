package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;
import methods.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addMood extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = mySql.getConnection();
        List<data> data = new ArrayList<>();
        String message;
        data st = new data();
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
            // 设置响应内容类型
            String star = request.getParameter("star");
            String value = request.getParameter("value");
            String text = request.getParameter("text");
            String time = request.getParameter("time");
            String img = request.getParameter("img");
            Log.log("发布心情:星级" + star + "感受:" + value + "内容:" + text, "'" + new Date().getTime() + "'", 2, "", "");
            System.out.println("评价为：" + star + "图片img：" + img);
            String sql = "insert mood (star,value,text,img,time) values ('" + star + "','" + value + "','" + text + "','" + img + "','" + time + "')";
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                st.setFlag(1);
                st.setCode("200");
                st.setMsg("添加成功");
            } else {
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("添加失败");
            }
            response.getWriter().println(JSONObject.toJSONString(st));//注意这里不是控制台输出了，是HttpServletResponse，用于返回json给http请求方
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {//关闭连接
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception ex) {
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
