package dao;

import DB.MyConnection;
import model.User;

import java.sql.*;

public class UsersDAO {

    public static boolean isExists(String email)throws SQLException {

        Connection connection = MyConnection.getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from users";

       ResultSet rs = statement.executeQuery(query);
       while(rs.next()){
           String e = rs.getString("email");
           if(e.equals(email))
               return true;
       }
       return false;
    }
     public static int saveUser(User user) throws SQLException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
       return  ps.executeUpdate();
     }


}
