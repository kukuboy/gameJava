package methods;

import DB.mySql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class powerAdd {

    public static boolean NumAdd(int id) throws SQLException, ClassNotFoundException {
//        Long startTs = System.currentTimeMillis();
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
        String sql = "select * from power where id = " + id;
        Connection conn = mySql.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            float p = Float.parseFloat(resultSet.getString("p"));
            float s = Float.parseFloat(resultSet.getString("s"));
            float t = Float.parseFloat(resultSet.getString("t"));
            String own =resultSet.getString("own");
            float end=p*s*t;
            float mon;
            mon = Integer.parseInt(own)+end;
            System.out.println(mon);
            sql = "update power set own = '" + mon + "' where id=" + id;
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                try
                {
                    Thread.sleep(1000);
                    NumAdd( id);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
