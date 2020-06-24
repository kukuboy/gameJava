package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class addUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
        List<data> data = new ArrayList<>();
        String message;
        data st = new data();
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
            // 设置响应内容类型
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String passWord = request.getParameter("passWord");
            System.out.println("姓名为："+name+"手机号为："+phone+"密码为:"+passWord);
            String sql = "select * from user where phone="+phone+" and passWord = "+passWord;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("该账号已经注册");
            } else {
                sql = "insert user (name,phone,passWord) values ('"+name+"',"+phone+","+passWord+")";
                int result = statement.executeUpdate(sql);
                if (result>0) {
                    st.setFlag(1);
                    st.setCode("200");
                    st.setMsg("注册成功");
                } else {
                    st.setFlag(0);
                    st.setCode("200");
                    st.setMsg("注册失败");
                }
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
}
