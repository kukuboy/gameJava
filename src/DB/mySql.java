package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class mySql {

    public static Connection getConnection(){
        Connection conn = null;//连接的实体
        try {
//            try {
//                PrintStream ps=new PrintStream("../log.txt");
//                System.setOut(ps);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            String DRIVER = "com.mysql.cj.jdbc.Driver";
            Class.forName(DRIVER);
            System.out.println("数据库驱动加载完成");
            String url = "jdbc:mysql://182.92.207.81:3306/game?serverTimezone=UTC";
            String user="root";
            String passWord="root12345";
            conn = DriverManager.getConnection(url,user,passWord);
            System.out.println("已成功的与mysql建立了连接");
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
