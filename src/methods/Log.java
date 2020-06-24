package methods;


import DB.mySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Log {

    public static boolean log(String describe, String time, int type, String to, String from) throws SQLException, ClassNotFoundException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
        to = "1712721716@qq.com";
        from = "2294211995@qq.com";
        String sql = "insert log (descr,time,fromUser,toUser,type) values ('" + describe + "'," + time + ",'" + from + "','" + to + "'," + type + ")";
        Connection conn = mySql.getConnection();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(sql);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
