package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;
import data.level;
import methods.powerAdd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class getLevel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
        Object data = new Object();
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
            int level = jsonObject.getInteger("level");
            System.out.println("level为："+level);
            String sql = "select * from level where level="+level;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                level sqlData = new level();
                sqlData.setLevel(resultSet.getInt("level"));
                sqlData.setBright(resultSet.getString("Bright"));
                sqlData.setDark(resultSet.getString("Dark"));
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("获取信息成功");
                data=sqlData;
                st.setData(data);
            } else {
                st.setFlag(1);
                st.setCode("200");
                st.setMsg("获取失败,请重新登录");
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
