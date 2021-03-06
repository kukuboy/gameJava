package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;
import data.power;

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

public class setPower extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
        List<power> power = new ArrayList<>();
        data st = new data();
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
            // 设置响应内容类型
            int id = Integer.parseInt(request.getParameter("id"));
            String email = request.getParameter("email");
            System.out.println("id为："+id);
            String sql = "UPDATE * from power where id="+id;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                power p = new power();
                p.setId(resultSet.getInt("id"));
                p.setLevel(resultSet.getInt("level"));
                p.setOwn(resultSet.getInt("own"));
                p.setP(resultSet.getInt("p"));
                p.setS(resultSet.getInt("s"));
                p.setT(resultSet.getInt("t"));
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("获取信息成功");
                power.add(p);
                st.setData(power);
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
