package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;
import data.user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
        Object user = new Object();
        data st = new data();
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
            // 设置响应内容类型

            //获取post参数
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            String str = sb.toString();
            JSONObject jsonObject = JSONObject.parseObject(str);
            //获取对应的值
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            System.out.println("邮箱为：" + email + "密码为:" + password + "送过来的值为:"+str);
            String sql = "select * from user where email='" + email + "' and password = " + password;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user u = new user();
                u.setName(resultSet.getString("name"));
                u.setId(resultSet.getInt("id"));
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("登录成功");
                user=u;
                st.setData(user);
            } else {
                st.setFlag(1);
                st.setCode("200");
                st.setMsg("登录失败,账号和密码不匹配");
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
