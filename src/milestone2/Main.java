package milestone2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This program is designed to take a csv file and populate it's data into
 * columns using javaFX
 *
 * @author Joshua Coss
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("propertyAssessment.fxml"));
        primaryStage.setTitle("Edmonton Property Assessments");
        primaryStage.setScene(new Scene(root, 1487, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * main - Launches the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
