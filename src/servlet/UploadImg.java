package servlet;

import com.alibaba.fastjson.JSONObject;
import data.data;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by foreknow on 2018/12/13.
 * MultipartConfig
 * 使用注解MultipartConfig 将一个 Servlet 标识为支持文件上传。Servlet3.0 将
 * multipart/form-data 的 POST 请求封装成 Part，通过 Part 对文件进行上传。
 * Servlet3 没有提供直接获取文件名的方法,需要从请求头中解析出来
 */
@MultipartConfig
public class UploadImg extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request);
        Part part = request.getPart("file");
        System.out.println(part);
        String name = part.getSubmittedFileName();
        System.out.println(name);
        UUID uuid = UUID.randomUUID();

        String path = request.getServletContext().getRealPath("") + "/pic/";
        File file = new File(path);
        System.out.println(file);
        if (!file.exists()) {
            file.mkdirs();
        }
        long time = new Date().getTime();
        part.write(path + uuid + "t" + time);
        request.getSession().setAttribute("pic", uuid + "t" + time);
        data st = new data();
        st.setFlag(1);
        st.setCode("200");
        st.setMsg(uuid + "t" + time);
        response.getWriter().println(JSONObject.toJSONString(st));//注意这里不是控制台输出了，是HttpServletResponse，用于返回json给http请求方
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
