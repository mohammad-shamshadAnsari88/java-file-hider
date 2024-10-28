package Views;

import dao.UsersDAO;
import model.User;
import service.GenerateOTP;
import service.SentOTP;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Welcome {

    public void WelcomeScreen() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the Application");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signUp");
        System.out.println("Press 0 to exit");

        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter  email");
        String email = sc.nextLine();

        try {
            if (UsersDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SentOTP.sendOTP(email, genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if(otp.equals(genOTP)) {
                   new UserView(email).home();
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private void signUp (){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter name");
            String name =sc.nextLine();
            System.out.println("Enter email");
            String email =sc.nextLine();

            String genOTP = GenerateOTP.getOTP();
            SentOTP.sendOTP(email, genOTP);
            System.out.println("Enter the OTP");

            String otp =sc.nextLine();
            if (otp.equals(genOTP)){
                User user = new User(name,email);

                int response = UserService.saveUser(user);
                switch (response){
                    case 0 -> System.out.println("User registerd");
                    case 1 -> System.out.println(" User already exists");
                }
            }else {
                System.out.println("Wrong OTP");
            }
        }
}

