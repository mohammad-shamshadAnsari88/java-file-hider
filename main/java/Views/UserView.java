package Views;

import dao.DataDao;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private  String email;
    UserView(String email){
        this.email =email;
    }
    public void home() throws SQLException {

        do{
            System.out.println("Welcome" + this.email);
            System.out.println(" Press 1 to show hidden files");
            System.out.println(" Press 2 to hide new files");
            System.out.println(" Press 3 to unhide  files");
            System.out.println(" Press 0 to to exit");

            Scanner sc = new Scanner(System.in);

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch){
                case 1 -> {
                    try {
                        List<Data> files = DataDao.getAllFiles(this.email);
                        System.out.println("ID  -  File name");
                        for(Data file: files){
                            System.out.println(file.getId() + "-" + file.getFilename());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                     String path = sc.nextLine();
                     File f = new File(path);
                     Data file = new Data(0, f.getName(),path,this.email);
                    try {
                        DataDao.hideFile(file);
                    }
                    catch (IOException e) {
                       e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<Data> files = DataDao.getAllFiles(this.email);
                    System.out.println("ID - File name");
                    for (Data file : files) {
                        System.out.println(file.getId() + "-" + file.getFilename());

                    }
                    System.out.println("Enter the id of file to unhide ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValid = false;

                    for (Data file : files){
                        if(file.getId() ==id){
                            isValid =true;
                            break;
                        }
                    }
                    if(isValid){
                        try {
                            DataDao.unhide(id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("wrong id");
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }
        while (true);
    }
}
