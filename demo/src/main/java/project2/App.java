package project2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;


import java.io.IOException;

/**
 * JavaFX App that extends an application
 * @author Keeley Mahoney and Joanne Liu
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Default constructor
     */
    public App()
    {

    }

    
    /**
     * Launches app with log in screen
     * @throws IOException if login screen cannot load
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginScreen"), 1048, 768);
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("server.css").toExternalForm());
    }

    /**
     * The functions switches the fxml screen 
     * @param fxml - the fxml file name
     * @throws IOException - if fxml file does not load
     */
    public static void setRoot(String fxml) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        if(fxml.equals("editMenu"))
        {
            EditMenuController editMenuController = fxmlLoader.getController();
            editMenuController.startIn();
        } else {
            scene.setRoot(loadFXML(fxml));
        }
        scene.setRoot(root);
    }

    /**
     * This function switches screens between the checkout and order screen for servers
     * @param fxml - fxml file name
     * @param menuItemQuantities - menu item names and quantities (HashMap(String, Integer))
     * @param clickedButtons - names of menu items buttons that are pressed (ArrayList(String))
     * @throws IOException - if fxml file doesn't load
     */
    public static void setRoot(String fxml, HashMap<String, Integer> menuItemQuantities, ArrayList<String> clickedButtons) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        if(fxml.equals("checkout")) {
            SecondaryController checkoutController = fxmlLoader.getController();
            checkoutController.buildScreen(clickedButtons, menuItemQuantities);
        } else if (fxml.equals("orderScreen")) {
            OrderScreenController orderScreenController = fxmlLoader.getController();
            orderScreenController.loadScreen(clickedButtons, menuItemQuantities);
        }
        scene.setRoot(root);
    }

    /**
     * This function switches the screen from manager menu to edit menu
     * @param fxml - name of fxml file 
     * @param button_name - name of the menu item to edit (string)
     * @throws IOException - if file does not load
     */
    public static void setRoot(String fxml, String button_name) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        //System.out.println("---------");
        //System.out.println("FXML URL: " + App.class.getResource(fxml + ".fxml"));
        //System.out.println("this is fxml: " + fxml);
        Parent root = fxmlLoader.load();

        
        if (fxml.equals("editMenu")) {
            EditMenuController editMenuController = fxmlLoader.getController();
            editMenuController.initData(button_name);
        }

        scene.setRoot(root);
    }

    /**
     * This function loads the fxml page
     * @param fxml - name of fxml file (string)
     * @return root node of the fxml 
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

   
    /**
     * Main function
     * @param args - list of parameters that are passed into maine
     */
    public static void main(String[] args) {
        launch();
    }

}