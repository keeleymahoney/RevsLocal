package project2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * This controller class is for the log-in screen.
 * @author Joanne Liu
 */
public class PrimaryController {

    /**
     * This text field is to get what user types in for employee ID.
     */
    @FXML
    private TextField inputEmployeeID;

    /**
     * This password field is for the password on the log-in page.
     */
    @FXML
    private PasswordField inputPassword;
    
    /**
     * This button is for the user to submit the log-in information.
     */
    @FXML
    private Button submitButton;

    /**
     * This label is to display any errors accordingly for log-in screen.
     */
    @FXML
    private Label errorMessage;

    /**
     * This function switches the page to the server screen.
     * @throws IOException when page fails to load
     */
    @FXML
    private void switchtoServerPage() throws IOException {
        // App.setRoot("checkout", clickedNames, quantities);
        App.setRoot("orderScreen");
    }


    /**
     * This function switches the page to the manager screen.
     * @throws IOException when page fails to load
     */
    @FXML
    private void switchtoManagerPage() throws IOException {
        // App.setRoot("checkout", clickedNames, quantities);
        App.setRoot("inventory");
    }

    
    /**
     * Default constructor of the class
     */
    public PrimaryController() {
        // default constructor
    }

    /**
     * Linked to the submit button and redirects to manager or server screens depending on the entered
     * employee login information. The function will also prompt error messages if the employee
     * log-in information do not match any entries in the database. 
     * @throws IOException when the page switching to fails to load
     */
    @FXML
    private void submitInfo() throws IOException {
        // Create instance of the employee list from database
        EmployeeList fullEmployeeList = new EmployeeList();
        LoginSystem loginManager = new LoginSystem(fullEmployeeList);
        int employeeID = 0;

        // loops until correct information is entered
        try {
            // Try to parse string to int
            employeeID = Integer.parseInt(inputEmployeeID.getText());
            if (loginManager.login(employeeID, inputPassword.getCharacters().toString())) {
                errorMessage.setText("Logging in...");
                if (fullEmployeeList.employees.get(employeeID).manager) {
                    // if employee's role is a manager
                    switchtoManagerPage();
                } else {
                    switchtoServerPage();
                }
            } else {
                errorMessage.setText("Incorrect Employee ID or password");
                inputEmployeeID.clear();
                inputPassword.clear();
            }

        } catch (NumberFormatException e) {
            errorMessage.setText("Employee ID must be integer");
        }

    }

}
