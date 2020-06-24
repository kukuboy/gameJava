package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// 判断上传表单是否为multipart/form-data类型
        // 中
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // System.out.println(System.getProperty("java.io.tmpdir"));//默认临时文件夹
//
//        // 2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
//        ServletFileUpload sfu = new ServletFileUpload(factory);
//        sfu.setSizeMax(10 * 1024 * 1024);// 以byte为单位 不能超过10M 1024byte =
//        // 1kb 1024kb=1M 1024M = 1G
//        sfu.setHeaderEncoding("utf-8");
//        List<FileItem> fileItemList = sfu.parseRequest(request.getParameter("file"));
//        Iterator<FileItem> fileItems = fileItemList.iterator();
        File file = new File(request.getParameter("file"));
        String fileName = file.getName();// 文件名称
        System.out.println("原文件名：" + fileName);// Koala.jpg

        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        System.out.println("扩展名：" + suffix);// .jpg

        // 新文件名（唯一）
        String newFileName = new Date().getTime() + suffix;
        System.out.println("新文件名：" + newFileName);// image\1478509873038.jpg

        // 5. 调用FileItem的write()方法，写入文件
        File newFile = new File("../../web/WEB-INF/img" + newFileName);
        System.out.println(file.getAbsolutePath());
//        file.write(newFile);

        // 6. 调用FileItem的delete()方法，删除临时文件
        file.delete();


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
