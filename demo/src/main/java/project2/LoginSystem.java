package project2;

import java.util.Scanner;

/**
 * This class is to authenticate the log in information entered by user
 * @author Vittoria Cicalese
 */
public class LoginSystem {
  
    private EmployeeList employeeList;
    private Scanner scanner;

    /**
     * Constructor 
     * @param employeeList - EmployeeList (EmployeeList Object)
     */
    public LoginSystem(EmployeeList employeeList) {
        this.employeeList = employeeList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * This function is to test whether the entered ID and password are correct.
     * @return Success of Login (boolean)
     */
    public boolean promptLogin() {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Please log in using your employee ID and password.");
            System.out.print("Employee ID: ");
            String idStr = scanner.nextLine(); 
            int id = Integer.parseInt(idStr);
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            loggedIn = login(id, password);
            if (!loggedIn) {
                System.out.println("Invalid credentials, please try again.");
            }
        }
        System.out.println("Login successful");
        return true;
    }

    /**
     * This function returns whether the user inputted matches database employee information
     * @param employeeId Employee ID (int)
     * @param password Employee Password (String)
     * @return Whether login credentials match database (boolean)
     */
    public boolean login(int employeeId, String password) {
        EmployeeList.Employee employee = employeeList.employees.get(employeeId);
        if (employee != null && employee.password.equals(password)) {
            return true;
        }
        return false;
    }
}
