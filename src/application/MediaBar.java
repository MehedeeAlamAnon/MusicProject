package application;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MediaBar extends HBox {
	Slider time = new Slider();
	Slider vol = new Slider();
	Button playButton;
	Button volume;
	Button previous, next, mute;
	MediaPlayer player;
	Label showtime, totaltime;
	boolean ismute;
	
	

	public MediaBar(MediaPlayer play) {
		
		ismute = false;
		Image imagePlay = new Image(getClass().getResourceAsStream("pause.png"));
		Image imagePause = new Image(getClass().getResourceAsStream("play.png"));
		Image imageVolume = new Image(getClass().getResourceAsStream("volume.png"));
		Image imageMute = new Image(getClass().getResourceAsStream("mute.png"));
		playButton=new Button();
		playButton.setStyle("-fx-background-color:#425051");
		playButton.setGraphic(new ImageView(imagePlay));
		
		volume=new Button();
		volume.setStyle("-fx-background-color:#425051");
		volume.setGraphic(new ImageView(imageVolume));
		play.stop();

		totaltime = new Label();
		totaltime.setStyle("-fx-text-fill:#fff;");
		totaltime.setPadding(new Insets(0, 5, 0, 5));

		player = play;
		setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 5, 0, 0));
		showtime = new Label();
		showtime.setStyle("-fx-text-fill:#fff;");
		showtime.setPrefWidth(60);
		showtime.setMinWidth(30);
		showtime.setPadding(new Insets(0, 0, 0, 10));

		vol.setPrefWidth(70);
		vol.setMinWidth(30);
		vol.setValue(100);
		vol.setPadding(new Insets(0, 0, 0, 10));

		//volume.setPadding(new Insets(0, 0, 0, 5));
		//volume.setStyle("-fx-text-fill:#fff;");
		

		//mute = new Button("mute");

		//mute.setPadding(new Insets(0, 5, 0, 5));

		HBox.setHgrow(time, Priority.ALWAYS);
		//playButton.setPrefWidth(30);

		getChildren().add(playButton);
		getChildren().add(showtime);
		getChildren().add(time);
		getChildren().add(totaltime);
		//getChildren().add(mute);
		getChildren().add(volume);
		getChildren().add(vol);

		setStyle("-fx-background-color:linear-gradient(#425051,#425051)");

		//mute.setStyle("-fx-background-color:green;-fx-text-fill:#fff;");
		volume.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (ismute == false) {
					ismute = true;
				} else if (ismute == true) {
					ismute = false;
				}

				if (ismute) {
					player.setMute(true);
					//mute.setStyle("-fx-background-color:#DE5246;-fx-text-fill:#fff;");
					volume.setStyle("-fx-background-color:#425051");
					volume.setGraphic(new ImageView(imageMute));
				} else {
					player.setMute(false);
					//mute.setStyle("-fx-background-color:green;-fx-text-fill:#fff;");
					volume.setStyle("-fx-background-color:#425051");
					volume.setGraphic(new ImageView(imageVolume));

				}

			}
		});

		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Status status = player.getStatus();
				if (status == Status.PLAYING) {
					
					if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())) {
						player.seek(player.getStartTime());
						playButton.setStyle("-fx-background-color:#425051");
						playButton.setGraphic(new ImageView(imagePlay));
						player.play();
					} else {
						player.pause();
						playButton.setStyle("-fx-background-color:#425051");
						playButton.setGraphic(new ImageView(imagePause));

					}
				}
				if (status == Status.PAUSED || status == Status.STOPPED || status == Status.HALTED) {

					player.play();
					playButton.setStyle("-fx-background-color:#425051");
					playButton.setGraphic(new ImageView(imagePlay));
				}

			}
		});

		time.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {

				if (time.isPressed()) {
					player.seek(player.getMedia().getDuration().multiply(time.getValue() / 100));
				}
			}
		});

		vol.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {

				if (vol.isPressed()) {
					player.setVolume((vol.getValue() / 100));
				}
			}
		});

		player.currentTimeProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {

				updatevalues();

			}
		});

	}

	protected void updatevalues() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				time.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
				showtime.setText(
						(int) player.getCurrentTime().toHours() + ":" + (int) (player.getCurrentTime().toMinutes() % 60)
								+ ":" + (int) player.getCurrentTime().toSeconds() % 60);

				totaltime.setText((int) player.getTotalDuration().toHours() + ":"
						+ (int) (player.getTotalDuration().toMinutes() % 60) + ":"
						+ (int) player.getTotalDuration().toSeconds() % 60);
			}
		});
	}

}
