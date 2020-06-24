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

public class addFood extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = mySql.getConnection();
        List<data> data = new ArrayList<>();
        String message;
        data st = new data();
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
            // 设置响应内容类型
            //获取post参数
//            StringBuffer sb = new StringBuffer();
//            InputStream is = request.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String s = "";
//            while ((s = br.readLine()) != null) {
//                sb.append(s);
//            }
//            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(sb));
//            ServletInputStream input = request.getInputStream();
//            String img = jsonObject.getString("img");
//            String name = jsonObject.getString("name");
            String name = request.getParameter("name");
            String img = request.getParameter("img");
            String classNum = request.getParameter("class");
            System.out.println("菜名为：" + name + "图片img：" + img);
            String sql = "select * from food where name='" + name + "'";
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                st.setFlag(0);
                st.setCode("200");
                st.setMsg("抱歉，该菜名已经存在");
            } else {
                sql = "insert food (name,img,num,class) values ('" + name + "','" + img + "'," + 0 + "," + classNum + ")";
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
