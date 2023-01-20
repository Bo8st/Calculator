package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static void main(String[] args) {
//		Calculator calc = new Calculator();
//		
//		String expression = " 12 * ( 3 + 4 ) ";
//		calc.express(expression);
//		System.out.println(calc.solver());
//		System.out.println(calc.expres(expression));
//		System.out.println(calc.eval());
		
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
		Scene scene = new Scene(root);
		Image icon = new Image("logo.png");
		
		String css = this.getClass().getResource("style.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		stage.setScene(scene);
		stage.setTitle("Calc");
		stage.getIcons().add(icon);
		stage.setResizable(false);
		stage.show();
		
	}
	
}
