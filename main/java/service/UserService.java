package service;

import dao.UsersDAO;
import model.User;

import java.sql.SQLException;

public class UserService {


    public static  Integer saveUser(User user){
        try {
            if(UsersDAO.isExists(user.getEmail())){
                return 0;
            }else {
                return UsersDAO.saveUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
           return null;
    }

}
