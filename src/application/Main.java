package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root = fxmlLoader.load(getClass().getResource("Dashboard.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setTitle("Runnymede Robotics Dashboard");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		launch(args);
	}
	
}
