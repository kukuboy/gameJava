package servlet;

import com.alibaba.fastjson.JSONObject;
import data.data;
import methods.FoodNum;
import methods.MailUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class Mail extends HttpServlet {
    data st = new data();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理前端请求
        String text = request.getParameter("text");
        String email = request.getParameter("email");
        String img = request.getParameter("img");
        boolean b = false;
        try {
            b = FoodNum.NumAdd(text);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (b) {
            System.out.println("增加完成");
        }
        //将信息封装进user对象
        Date date = new Date();
        try {
            // date.getTime()+""是用时间戳作为校验码发送给对方邮箱
            MailUtil.sendActiveMail(email, text, img, date.getTime() + "");
            st.setFlag(1);
            st.setCode("200");
            st.setMsg("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            st.setFlag(0);
            st.setCode("200");
            st.setMsg("发送失败");
        }
//        try {
//            PrintStream ps = new PrintStream("../../log.txt");
//            System.setOut(ps);
//            System.out.println(st);
//            System.out.println(date.getTime());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        System.out.println(st);
        System.out.println(date.getTime());
        response.getWriter().println(JSONObject.toJSONString(st));//注意这里不是控制台输出了，是HttpServletResponse，用于返回json给http请求方
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
