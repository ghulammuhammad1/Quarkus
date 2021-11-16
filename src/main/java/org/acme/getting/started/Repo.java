package org.acme.getting.started;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.agroal.api.AgroalDataSource;

@ApplicationScoped
public class Repo {
    @Inject
    AgroalDataSource agr;

    Connection createConnection() {
        Connection conn = null;
        try {
            // "jdbc:mysql://172.17.0.2:3306/quarkus", "root", "123"
            conn = agr.getConnection();
            System.out.print("Connection: " + conn);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return conn;
    }

    public Student getStudent(int id) {
        System.out.println("id" + id);
        String sql = "select * from student where id = ?";
        Student student = null;
        try {
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            System.out.println("result" + result);
            student = new Student();
            while (result.next()) {
                System.out.println(result.getInt(1) + " --- " + result.getString(2));
                student.setId(result.getInt(1));
                student.setName(result.getString(2));

            }
        } catch (SQLException ae) {
            ae.printStackTrace();
        }
        return student;
    }

    public void setStudent(Student student) {

        String sql = "INSERT INTO student (name) VALUES (?)";
        try {
            createConnection();
            System.out.println("student" + student.getName());
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.setString(1, student.getName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }

    }

    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> list = new ArrayList<Student>();
        String sql = "select * from student";
        try {
            PreparedStatement statement = createConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            System.out.println("result" + result);
            while (result.next()) {
                // System.out.println(result.getString(2));
                Student m = new Student();
                m.setId(result.getInt(1));
                m.setName(result.getString(2));
                list.add(m);
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }

        return list;
    }

    public void update(Student std) {
        String sql = "UPDATE student SET name = ? where id = ?";
        try {
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.setString(1, std.getName());
            statement.setInt(2, std.getId());
            statement.executeUpdate();
           
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
    }

    public void delete(int id){
        String querry="DELETE FROM student WHERE id=?";
        try {
            PreparedStatement statement=createConnection().prepareStatement(querry);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}