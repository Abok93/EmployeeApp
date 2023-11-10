package java2_project_bokrossy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeApp extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("project.fxml"));
    Parent root = loader.load();
    EmployeeController controller = loader.getController();

    // Call the loadEmployees method and the updateUI method
    controller.loadEmployees();
    controller.updateUI();

    // Show the main window
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);

  }

}
