package project2;

import java.io.IOError;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
/**
 * Class for tracking current employee information
 * @author Brandon Cisneros
 */
public class EmployeeList {
    /**
     * Map of Employee ID to Employee Object (HashMap(Integer, Employee))
     */
    public HashMap<Integer, Employee> employees;
    /**
     * Class for tracking specific employee Details
     * @author Brandon Cisneros
     */
    public class Employee{
        /**
         * Employee ID (String)
         */
        public int employeeId;
        /**
         * Manager Status (boolean)
         */
        public boolean manager;
        /**
         * Employee Name (String)
         */
        public String employeeName;
        /**
         * Employee Password (String)
         */
        public String password;

        /**
         * Constructor for creating Employee Class
         * @param idIn Employee ID (int)
         * @param manIn Manager Status (boolean)
         * @param nameIn Employee Name (String)
         * @param pwdIn Employee Password (String)
         */
        Employee(int idIn, boolean manIn, String nameIn, String pwdIn) {
            employeeId = idIn;
            manager = manIn;
            employeeName = nameIn;
            password = pwdIn;
        }
    }
    /**
     * Constructor for EmployeeList
     * @throws IOException when populateEmployees() throws exception
     */
    public EmployeeList () throws IOException {
        this.employees = new HashMap<Integer, Employee>();
        populateEmployees();
    }

    
    /** 
     * Populates object with employee data from PostgreSQL database
     * @throws IOException when text manipulation fails
     */
    public void populateEmployees() throws IOException {
        DatabaseHandler db = new DatabaseHandler();
        String query = "SELECT * FROM employee;";
        try (Statement statement = db.getConn().createStatement();
        ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int empId = rs.getInt("employee_id");
                boolean manag = rs.getBoolean("manager");
                String empName = rs.getString("employee_name");
                String empPwd = rs.getString("pswd");
                Employee emp = new Employee(empId, manag, empName, empPwd);
                employees.put(empId, emp);                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Adds or edits an employee's information in the object and in the PosgreSQL database
     * @param empId Employee ID (int)
     * @param manIn Manager Status (boolean)
     * @param nameIn Employee Name (String)
     * @param pwdIn Employee Password (String)
     * @throws IOException when DatabaseHandler fails I/O
     */
    public void editEmployee(int empId, boolean manIn, String nameIn, String pwdIn) throws IOException {
        Employee empEdit = new Employee(empId, manIn, nameIn, pwdIn);
        if (employees.containsKey(empId)) {
            employees.put(empId, empEdit);
            String query = "UPDATE employee SET employee_id = " + Integer.toString(empId) + ", manager = " + Boolean.toString(manIn) + ", employee_name = '" + nameIn + "', pswd = '" + pwdIn + "' WHERE employee_id = " + Integer.toString(empId) + ";";
            System.out.println(query);
            DatabaseHandler db = new DatabaseHandler();
            db.executeSQLCommand(query);
            db.closeConnection();

        }
        else {
            employees.put(empId, empEdit);
            String query = "INSERT INTO employee (employee_id, manager, employee_name, pswd) VALUES (" + Integer.toString(empId) + ", " + Boolean.toString(manIn) + ", '" + nameIn + "', '" + pwdIn + "');";
            DatabaseHandler db = new DatabaseHandler();
            db.executeSQLCommand(query);
            db.closeConnection();
        }
    }
    /**
     * Removes an employee's information in the object and in the PosgreSQL database
     * @param empId Employee ID (int)
     * @throws IOException when DatabaseHandler fails I/O
     */
    public void removeEmployee(int empId) throws IOException {
        if (employees.containsKey(empId)) {
            employees.remove(empId);
            String query = "DELETE FROM employee WHERE employee_id = '" + Integer.toString(empId) + "';";
            DatabaseHandler db = new DatabaseHandler();
            db.executeSQLCommand(query);
            db.closeConnection();
        }

    }

    // Test Main
    // public static void main (String[] args) {
    //     EmployeeList myList = new EmployeeList();
    //     myList.populateEmployees();
    //     myList.editEmployee(21, true, "Richard Nixon", "Watergate");
    //     myList.editEmployee(9, false, "Sofia Patel", "NEWPASS");
    //     myList.removeEmployee(14);
    // }

}