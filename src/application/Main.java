package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage)  {
		try {
			ViewLoader<AnchorPane, TurtleViewController> viewLoader = new ViewLoader<>("TurtleView.fxml");
			
			AnchorPane sceneRoot = viewLoader.getLayout();
			TurtleViewController turtleController = viewLoader.getController();
			turtleController.setCourseStage(primaryStage);
			
			Scene scene = new Scene(sceneRoot);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("Żółwik");
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
