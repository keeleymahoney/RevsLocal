package project2;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
 * This class is for the screen that displays the employee list
 * @author Joanne Liu
 */
public class EmployeeListController {

    /**
     * Button to switch to inventory page.
     */
    @FXML
    private Button inventory;

    /**
     * FXML VBox to display the employees.
     */
    @FXML
    private VBox employeeBox;

    /**
     * FXML Button to submit changes.
     */
    @FXML
    private Button submit;

    /**
     * Hashset that store employeeIDs of employees the user wants to change roles for.
     */
    Set<Integer> employeesChangeRoles = new HashSet<>();

    /**
     * Hashset that stores employeeIDs of employees user wants to remove.
     */
    Set<Integer> employeesRemove = new HashSet<>();

    /**
     * Hashmap of integer and EmployeeList.Employees that maps the ID to the new employees the user wants to add.
     */
    HashMap<Integer, EmployeeList.Employee> allNewEmployees = new HashMap<>();

    /**
     * EmployeeList to store the employee list retrieved from database.
     */
    EmployeeList employeeManager;

    /**
     * FXML Label to display error message for new employee inputs
     */
    @FXML
    Label errorMessage;

    /**
     * FXML ComboBox for a drop down menu for the new employee role 
     */
    @FXML
    ComboBox<String> roleDropDown;

    /**
     * FXML Textfield for new Employee ID input
     */
    @FXML
    TextField idInput;

    /**
     * FXML Textfield for new employee name input
     */
    @FXML
    TextField nameInput;

    /**
     * FXML Password field for new employee log-in password
     */
    @FXML
    PasswordField passwordInput;

    /**
     * Switches to the manager menu screen
     * @throws IOException if manager menu screen does not load
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("managerMenu");
    }


    /**
     * Switches to inventory screen
     * @throws IOException - if inventory screen doesn't load
     */
    @FXML
    private void switchToPrimary() throws IOException {
        try {
            App.setRoot("inventory");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /**
     * Switches to edit inventory page
     * @throws IOException - if screen doesn't load
     */
    @FXML
    private void switchToFifth() throws IOException {
        App.setRoot("editInventory");
    }

    /**
     * Switches to supplier order screen
     * @throws IOException - if screen does not load
     */
    @FXML
    private void switchToSupplierOrder() throws IOException {
        App.setRoot("SupplierOrdersScren");
    }

    /**
     * Switches to trends screen
     * @throws IOException - if screen doesn't load
     */
    @FXML
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
    }

    /**
     * Default constructor for EmployeeListController class
     */
    public EmployeeListController() {
        // default constructor
    }

  
    /**
     * This function initializes the employee list page and retrieves information from the database
     * @throws IOException when EmployeeList class fails to create
     */
    @FXML
    private void initialize() throws IOException {
        // connect to backend
        employeeManager = new EmployeeList();
        employeesChangeRoles.clear();
        employeesRemove.clear();
        employeeBox.getChildren().clear();

        for (Map.Entry<Integer, EmployeeList.Employee> entry : employeeManager.employees.entrySet()) {
            addEmployeeRow(entry.getValue());
        }
    
        // Add options for the roles drop down
        roleDropDown.getItems().addAll("Server", "Manager");

        // Clear error message
        errorMessage.setText("");
    }

    /**
     * This function displays a row of information for each employee in employee list from database, including ID, name and role
     * @param newEmployee - EmployeeList.Employee instance of an employee to display
     */
    @FXML
    private void addEmployeeRow(EmployeeList.Employee newEmployee) {
        HBox row = new HBox();
        row.getStyleClass().add("employee-row");
        Label role = new Label();
        if (newEmployee.manager) {
            role.setText("Manager");
            // change backend role
        } else {
            role.setText("Server");
        }

        // Display ID
        role.getStyleClass().add("employee-text");
        row.getChildren().add(role);

        Label id = new Label(Integer.toString(newEmployee.employeeId));
        id.getStyleClass().add("employee-text");
        row.getChildren().add(id);

        // Display name
        Label name = new Label(newEmployee.employeeName);
        name.getStyleClass().add("employee-name");
        row.getChildren().add(name);

        Button changeRole = new Button("Change Role");
        changeRole.getStyleClass().add("employee-button");

        // pass in current role of employee
        final boolean currRole = newEmployee.manager;

        changeRole.setOnAction(event -> switchRole(newEmployee.employeeId, role, currRole));
        row.getChildren().add(changeRole);

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("employee-button-2");
        row.getChildren().add(deleteButton);

        employeeBox.getChildren().add(row);
        deleteButton.setOnAction(event -> delete(newEmployee.employeeId, row));
    }


    /**
     * This function is called when the user presses on a "change role" button for an employee.
     * @param employeeID - Integer
     * @param role - Label that states the role of the employee
     * @param currRole - Boolean of whether employee is a manager
     */
    @FXML
    private void switchRole(Integer employeeID, Label role, boolean currRole) {
        // Boolean to store whether they are still manager
        boolean manager;
        if (role.getText() == "Manager") {
            role.setText("Server");
            manager = false;
        } else {
            role.setText("Manager");
            manager = true;
        }
        role.getStyleClass().add("employee-text-change");
        if (manager != currRole) {
            employeesChangeRoles.add(employeeID);
        } else {
            if (employeesChangeRoles.contains(employeeID)) {
                employeesChangeRoles.remove(employeeID);
            }
        }
    }


    /**
     * This function is called when the user presses on the delete button of an employee
     * @param employeeID - Integer
     * @param row - FXML HBox for the specific employee
     */
    @FXML
    private void delete(Integer employeeID, HBox row) {
        // App.setRoot("checkout", clickedNames, quantities);
        row.getChildren().clear();
        // add to vector of employees to delete
        employeesRemove.add(employeeID);
    }


    /**
     * This function is called when the user presses the "Submit changes" button. The database is updated
     * with all the submitted changes. 
     * @param event - ActionEvent performed on the button
     * @throws IOException - if EmployeeList class does not load
     */
    @FXML
    public void saveChanges(ActionEvent event) throws IOException {
        EmployeeList employeeManager = new EmployeeList();
        for (Integer ID : employeesChangeRoles) {
            EmployeeList.Employee curr = employeeManager.employees.get(ID);
            employeeManager.editEmployee(ID, !curr.manager,
                    curr.employeeName, curr.password);
        }

        for (Integer ID : employeesRemove) {
            employeeManager.removeEmployee(ID);
        }

        for(Map.Entry<Integer, EmployeeList.Employee> entry : allNewEmployees.entrySet()) {
            EmployeeList.Employee newEmployee = entry.getValue();
            employeeManager.editEmployee(entry.getKey(), newEmployee.manager,
                    newEmployee.employeeName, newEmployee.password);
        }
        // refresh the employee list
        initialize();
    }

    /**
     * This function is called when the user clicks on the add employee button. It checks
     * whether all the information is valid, such as whether the ID is taken or whether the password is blank.
     */
    @FXML
    public void addEmployee() {
        // set manager role boolean from drop down menu
        boolean newManagerBool;
        if(roleDropDown.getValue() == "Manager") {
            newManagerBool = true;
        } else {
            newManagerBool = false;
        }

        Integer newEmployeeID;
        String newName;
        String newPasswd;
        try {
            // Try to parse string to int
            newEmployeeID = Integer.parseInt(idInput.getText());
            newName = nameInput.getText();
            newPasswd = passwordInput.getText();
            if (!employeeManager.employees.containsKey(newEmployeeID) || newPasswd.isEmpty()) {
                // Create instance with entered information
                EmployeeList.Employee newEmployee = employeeManager.new Employee(newEmployeeID, newManagerBool, newName, newPasswd);
                allNewEmployees.put(newEmployeeID, newEmployee);
                // Add new employee to the list displayed
                addEmployeeRow(newEmployee);

                // Reset front-end text boxes and labels
                errorMessage.setText("");
                roleDropDown.setValue("");
                idInput.clear();
                passwordInput.clear();
                nameInput.clear();
                errorMessage.setText("Employee Added!");
            } else {
                // Print error message
                errorMessage.setText("ID taken or invalid password");
                idInput.clear();
                passwordInput.clear();
            }
        } catch (NumberFormatException e) {
            errorMessage.setText("Employee ID must be integer");
        }
        
    }
}
