package application;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	Button admin, listener;
	Label wc;
	BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		HBox box = new HBox();
		
		Image imageAdmin = new Image(getClass().getResourceAsStream("admin.png"));
		Image imageListener = new Image(getClass().getResourceAsStream("listener.png"));
		Image imageWelcome = new Image(getClass().getResourceAsStream("listener.png"));

		
		wc=new Label();
		wc.setPadding(new Insets(10,0,10,0));
		wc.setGraphic(new ImageView(imageWelcome)); 
		
		admin =new Button();
		admin.setGraphic(new ImageView(imageAdmin));
		
		listener = new Button();
		listener.setGraphic(new ImageView(imageListener));
		listener.setStyle("-fx-background-color:fff");

		box.getChildren().addAll(admin, listener);
		box.setPadding(new Insets(100, 0, 0, 350));
		box.setSpacing(10);
		Image image=new Image("file:background4.jpg");
		BackgroundImage myBI= new BackgroundImage(image,
			       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
			        BackgroundSize.DEFAULT);

		box.setBackground(new Background(myBI));

		

		try {
			root = new BorderPane();
			root.setCenter(box);

		} catch (Exception e) {
			e.printStackTrace();
		}
		admin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Admin admin = new Admin();

			}
		});
		listener.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Listener listener = new Listener();

			}
		});


		
		Scene scene = new Scene(root, 1100, 450);
		
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
