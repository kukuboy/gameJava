package methods;

import DB.mySql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodNum {

    public static boolean NumAdd(String name) throws SQLException, ClassNotFoundException {
        Long startTs = System.currentTimeMillis();
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);//反射JDBC包，这个一定要加，不然会报错
        String sql = "select * from food where name = '" + name + "'";
        Connection conn = mySql.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int Num = 0;
        if (resultSet.next()) {
            Num = resultSet.getInt("num");
            Num = Num + 1;
            sql = "update food set num = " + Num + " where name='" + name + "'";
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
