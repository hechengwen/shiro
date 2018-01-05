package shiro.hcw.jdbc;

import java.sql.*;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/26 17:47
 * Description:
 * Others:
 */
public class ConnectionData {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shiro", "root", "root");
            long start = System.currentTimeMillis();
            statement = connection.createStatement();
            System.out.println("数据库连接成功...");
//            statement.executeUpdate("insert into test (id,name,age) values (1,\'Tom\',20)");
//            statement.executeUpdate("UPDATE test SET `name` = 'xiaohei' WHERE id = 1");
            resultSet = statement.executeQuery("SELECT * FROM test WHERE id = 1");
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id") + "\t");
                System.out.print(resultSet.getString("name") + "\t");
                System.out.print(resultSet.getInt("age") + "\t");
                System.out.println();
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时(ms):" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

