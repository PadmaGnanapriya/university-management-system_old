/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universitymanagementsystem;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author gnanapri_se17014
 */
public class Courses {
    String course_name;
    int lecturer;
    
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    
    Scanner input = new Scanner(System.in);
    Lecturer lecturerObject = new Lecturer();
    public Courses(){
        
    }
    
    public void registerCourses(){
        String courseCode;
        String courseName;
        int lecturer;
        
        System.out.println("\nCourse code :");
        courseCode=input.nextLine();
        System.out.println("\nCourse Name :");
        courseName=input.nextLine();
        System.out.println("\nSelect the lecturer :");
        lecturerObject.previewLecturers();
        lecturer=input.nextInt();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            String query = "insert into courses (course_code,course_name,lecturer) values ('"+courseCode+"','"+courseName+"',"+lecturer+");";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Course registration was completed");
            con.close();
        }catch(Exception e){
               System.out.println("Error with database connection"+e);
        }
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            String query1 = "CREATE TABLE "+courseCode+"(id int(5) NOT NULL,student_id int(5) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement preparedStmt = con.prepareStatement(query1);
            preparedStmt.execute();
            String query2 = "ALTER TABLE "+courseCode+" ADD PRIMARY KEY (`id`), ADD KEY `"+courseCode+"_ibfk_1` (`student_id`);";
            preparedStmt = con.prepareStatement(query2);
            preparedStmt.execute();
            String query3 = "ALTER TABLE "+courseCode+" MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;";
            preparedStmt = con.prepareStatement(query3);
            preparedStmt.execute();
            String query4 = "ALTER TABLE "+courseCode+" ADD CONSTRAINT `"+courseCode+"_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);";
            preparedStmt = con.prepareStatement(query4);
            preparedStmt.execute();
            System.out.println("Course table was created..");
            con.close();
        }catch(Exception e){
               System.out.println("Error with database connection"+e);
        }
        
        
    }
    
    public void previewCourses(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
            Statement stmt=con.createStatement();
            rs=stmt.executeQuery("select * from courses;");
            
            while(rs.next()){
                System.out.println("["+rs.getInt("id")+"] "+rs.getString("course_code")+" - "+rs.getString("course_name"));
               
            }
            con.close();
        }catch(Exception e){
            System.out.println("Error with database connection"+e);
        }
    }
    
    public void enrollCourses(String input,String student){
        String temp="";
        int tempInt;
        int studentId=0;
        
        String[] values = input.split(",");
        for(int x=0;x<values.length;x++){
                tempInt=Integer.parseInt(values[x]);
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
                    Statement stmt=con.createStatement();
                    rs=stmt.executeQuery("select * from courses;");
                    while(rs.next()){
                         if(tempInt==rs.getInt("id")){
                             temp=rs.getString("course_code");
                         }
                     }
                    con.close();
                }catch(Exception e){
                System.out.println("Error with database connection"+e);
                }
                
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
                    Statement stmt=con.createStatement();
                    rs=stmt.executeQuery("select * from student;");
                    while(rs.next()){
                         if(student.equals(rs.getString("username"))){
                             studentId=rs.getInt("id");
                         }
                     }
                    con.close();
                }catch(Exception e){
                System.out.println("Error with database connection"+e);
                }
                
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
                    String query = "insert into "+temp+" (student_id) values ('"+studentId+"');";
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.execute();
                    
                    System.out.println("Couese enrollment is completed...");
                    con.close();
                }catch(Exception e){
                    System.out.println("Error with database connection"+e);
                }    
             temp="";   
        }
        
        
    }
    
    public void previewEnrolledCourses(int id){
        try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
                    Statement stmt=con.createStatement();
                    rs=stmt.executeQuery("select * from courses;");
                    while(rs.next()){
                         String course = rs.getString("course_code");
                         int courseID = rs.getInt("id");
                         try{
                             Class.forName("com.mysql.jdbc.Driver");
                                Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/university_management_system","root","");
                            Statement stmt2=con2.createStatement();
                            ResultSet rs2
                                    =stmt2.executeQuery("select * from "+course+";");
                            while(rs2.next()){
                                if(id==rs2.getInt("student_id")){
                                    System.out.println("["+courseID+"] "+rs.getString("course_code")+" "+rs.getString("course_name"));
                                }
                            }
                            con2.close();
                        }catch(Exception e){
                            System.out.println("Error with database connection 123"+e);
                        }
                         
                     }
                    con.close();
                }catch(Exception e){
                System.out.println("Error with database connection"+e);
                }
        
    }
}
