/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitymanagementsystem;
import java.io.IOException;
import java.util.Scanner;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gnanapri_se17014
 */
public class Student {
    String name;
    String username;
    String password;
    String courses;
    int age;
    int id;
    
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    
    Scanner input=new Scanner(System.in);
    Courses courseObject = new Courses();
    public Student(){
        
    }
    
    public void registerStudent(){
        System.out.println("\n---Student Registration Form---\n");
        System.out.println("Name :");
        name = input.nextLine();
        System.out.println("\nUsername :");
        username = input.nextLine();
        System.out.println("\nPassword :");
        password=input.nextLine();
        System.out.println("\nAge :");
        age=input.nextInt();
        System.out.println("\nSelect the courses :\n");
        courseObject.previewCourses();
        courses = input.nextLine();
        courses = input.nextLine();
        if(checkUsername(username)){
            try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            String query = "insert into student (name,username,password,age) values ('"+name+"','"+username+"','"+password+"',"+age+");";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Registration was completed");
            con.close();
            courseObject.enrollCourses(courses,username);
            System.out.println("\nYou have successfully registered for following courses");
            courseObject.previewCourses();
            System.out.println("\nThank you!");
            }catch(Exception e){
               System.out.println("Error with database connection"+e);
            }
        }else{
            System.out.println("This username is not available, Press enter to try again...");
            try {
                int check=System.in.read();
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            registerStudent();
        }
    }
    
    //Checks the availability of the username
    public boolean checkUsername(String uName){
        boolean ans = true;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select username from student;");
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
    
    //Login
    public void loginStudent(){
        
        System.out.println("\nUsername :");
        username = input.nextLine();
        System.out.println("\nPassword :");
        password=input.nextLine();
        
        
        int pChecker=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select * from student where username ='"+username+"';");
            while(rs.next()){
                String dbPassword = rs.getString("password");
                if(dbPassword.equals(dbPassword)){
                        pChecker=1;
                        name = rs.getString("name");
                        id = rs.getInt("id");
                }
            }
            con.close();
            //proceed to login menu
            if(pChecker==1){
                studentMenu(name,id,username);
            }else{
                System.out.println("Login failed, Try again....");
                loginStudent();
            }
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
    }
    
    public void studentMenu(String name,int id,String username){
        System.out.println("Logged In.....");
        System.out.println("\nHi "+name+"!");
        System.out.println("\nYou have registered for following courses : \n");
        courseObject.previewEnrolledCourses(id);
        int selector=0;
        while(selector!=1){
            System.out.println("\nPlease select a option");
            System.out.println("[1] Exit");
            System.out.println("[2] Register new course");
            selector = input.nextInt();
            if(selector==2){
                courseObject.previewCourses();
                courses = input.nextLine();
                courseObject.enrollCourses(courses, username);
                courseObject.previewEnrolledCourses(id);
            }
        }
        
    }
}
