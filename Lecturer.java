/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitymanagementsystem;
import java.util.Scanner;
import java.sql.*;
/**
 *
 * @author gnanapri_se17014
 */
public class Lecturer {
    String name;
    String username;
    String password;
    
    Scanner input= new Scanner(System.in);
    
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    public Lecturer(){
     
    }
    
    public void registerLecturer(){
        System.out.println("\nLecturer Registration Form\n");
        System.out.println("Name :");
        name = input.nextLine();
        System.out.println("\nUsername :");
        username = input.nextLine();
        System.out.println("\nPassword :");
        password=input.nextLine();
        
        if(checkUsername(username)){
            try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            String query = "insert into lecturer (name,username,password) values ('"+name+"','"+username+"','"+password+"');";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Registration was completed.");
            con.close();
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
        }else{
            System.out.println("This username is not available, please try again...");
            registerLecturer();
        }
       
    }
    
    //Checks the availability of the username
    public boolean checkUsername(String uName){
        boolean ans = true;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select username from lecturer;");
            while(rs.next()){
                if(uName.equals(rs.getString("username"))){
                ans =false;
                 }
            }
            con.close();
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
        return ans;
    }
    
    public void loginLecturer(){
        System.out.println("\nUsername :");
        username = input.nextLine();
        System.out.println("\nPassword :");
        password=input.nextLine();
        
        int pChecker=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select * from lecturer where username ='"+username+"';");
            while(rs.next()){
                String dbPassword = rs.getString("password");
                if(dbPassword.equals(dbPassword)){
                        pChecker=1;
                }
            }
            con.close();
            //proceed to login menu
            if(pChecker==1){
                lecturerMenu();
            }else{
                System.out.println("Login failed, Try again....");
                loginLecturer();
            }
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
    }
    
    public void lecturerMenu(){
        System.out.println("Logged In.....");
    }
    
    public void previewLecturers(){
        System.out.println("\n---Lecturers---\n");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select * from lecturer;");
            while(rs.next()){
                System.out.println("["+rs.getInt("id")+"] "+rs.getString("name"));
            }
            con.close();
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
    }
    
    
}
