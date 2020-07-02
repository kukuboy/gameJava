package servlet;

import DB.mySql;
import com.alibaba.fastjson.JSONObject;
import data.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class updatePower extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
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
            String stir = "";
            while ((stir = br.readLine()) != null) {
                sb.append(stir);
            }
            String str = sb.toString();
            JSONObject jsonObject = JSONObject.parseObject(str);
            String sql = "update power set";
            boolean early = false;
            //获取对应的值
            int id = jsonObject.getInteger("id");
            try {
                double own = jsonObject.getInteger("own");
                System.out.println("修为为：" + own);
                sql += " own=" + own;
                early = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                double money = jsonObject.getInteger("money");
                System.out.println("金钱为：" + money);
                if (early) {
                    sql += ",";
                }
                early = true;
                sql += " money=" + money;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int level = jsonObject.getInteger("level");
                System.out.println("级别为：" + level);
                if (early) {
                    sql += ",";
                }
                early = true;
                sql += " level=" + level;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                float p = jsonObject.getInteger("p");
                System.out.println("p为：" + p);
                if (early) {
                    sql += ",";
                }
                early = true;
                sql += " p=" + p;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                float s = jsonObject.getInteger("s");
                System.out.println("s为：" + s);
                if (early) {
                    sql += ",";
                }
                early = true;
                sql += " s=" + s;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                float t = jsonObject.getInteger("t");
                System.out.println("t为：" + t);
                if (early) {
                    sql += ",";
                }
                sql += " t=" + t;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sql += " where id=" + id;
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                st.setFlag(1);
                st.setCode("200");
                st.setMsg("更改成功");
            } else {
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("更改失败");
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
