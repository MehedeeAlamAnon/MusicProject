package application;

import java.io.File;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;

public class Admin {

	// admin
	Button admin, listener;
	BorderPane root;
	public Label username;
	public TextField user;
	public Label password, l;
	public PasswordField pass;
	public Button login;
	//update 
	public int update_song_id,update_artist_id,update_album_id;
	public String update_artist_name,update_album_name,update_song_name,update_duration;

	// admin panel
	public Media media = null;
	public MediaPlayer mediaPlayer = null;
	public static String source, src, sr;

	public Connection con;
	public String song_name;
	public Label songLabel;
	public TextField inputSong;

	public String artistname;
	public Label artistLabel;
	public TextField inputAtrist;

	public String albumname;
	public Label albumLabel;
	public TextField inputAlbum;

	public String duration;
	public Label durationLabel;
	public TextField inputDuratin;

	public Label notice;
	public Button playselect;
	public Button submit;
	public Button delete;
	public Button update;
	public Button deletealbumartist, adminallmusic, adminalbum, adminartist,
			adminload;
	public String deletesong, deletealbum, deleteartist;

	int adminallmusicbuttonflag = 0, adminalbumbuttonflag = 0,
			adminartistbuttonflag = 0;

	int albumid, artistid;
	int song_id_update = 0, song_hit_update = 0;
	int loadalbumflag = 0, loadartistflag = 0;
	int loadallmusicflag = 1;

	int artistbuttonflag = 0, albumbuttonflag = 0;
	int adminfunctionflag = 0;
	int loadplaylistflag = 0;

	String song_name_playlist, duration_playlist;
	int artist_id_playlist, album_id_playlist, hit_playlist;

	TableView<AllMusic> table;
	
	Image imageLogin= new Image(getClass().getResourceAsStream("login.png"));
	
	Image image=new Image("file:background4.jpg");
	BackgroundImage myBI= new BackgroundImage(image,
		       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
		        BackgroundSize.DEFAULT);


	Stage window;

	public Admin() {
		window = new Stage();
		root = new BorderPane();
		// admin panel
		notice = new Label();

		update=new Button("Update");
		update.setMinWidth(100);
		con = SqliteConnection.Connector();

		songLabel = new Label("Song name");
		songLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		artistLabel = new Label("Artist name");
		artistLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		albumLabel = new Label("Album name");
		albumLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		durationLabel = new Label("Duration");
		durationLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");
		notice = new Label("");
		notice.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		inputSong = new TextField();

		inputAtrist = new TextField();

		inputAlbum = new TextField();

		inputDuratin = new TextField();

		submit = new Button("Add Music");
		submit.setMinWidth(100);

		delete = new Button("Delete Music");
		delete.setMinWidth(100);
		deletealbumartist = new Button("Delete Album/Artist");
		deletealbumartist.setMinWidth(100);
		/*
		 * adminalbum=new Button("Album"); adminalbum.setMinWidth(100);
		 * adminartist=new Button("Artist"); adminartist.setMinWidth(100);
		 * adminload=new Button("Load"); adminload.setMinWidth(100);
		 */
		// admin panel

		// admin login

		login = new Button();
		login.setGraphic(new ImageView(imageLogin));
		
		l = new Label();
		l.setMinWidth(100);
		l.setStyle("-fx-text-fill:white ; -fx-font-size: 10pt;");

		username = new Label("Admin user");
		username.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		username.setMinWidth(100);
		user = new TextField();
		user.setPromptText("user");
		user.setMinWidth(100);

		password = new Label("Password");
		password.setStyle("-fx-text-fill: white; -fx-font-size: 10pt;");

		password.setMinWidth(100);

		pass = new PasswordField();
		pass.setPromptText("password");
		pass.setMinWidth(100);
		
		// admin login;

		File f = new File("D:/server/Tamak Pata.mp3");
		src = f.toURI().toString();
		media = new Media(src);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.stop();

		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!user.getText().isEmpty() && !pass.getText().isEmpty()) {
					String name = user.getText().toString();
					String password = pass.getText().toString();

					if (name.equals("abc") && password.equals("123")) {

						
						root.setCenter(adminFunction());
					} else {
						//l.setText("Invalide Input");
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("Username or Password not matched");
						alert.showAndWait();
						user.setText("");
						pass.setText("");
					}

				} else {
					//l.setText("Enter user and password");
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Enter all fields");
					alert.showAndWait();
				}
			}
		});
		root.setCenter(adminLogin());

		
		Scene scene = new Scene(root, 1100, 450);
		window.setScene(scene);
		window.show();

	}

	public BorderPane adminLogin() {
		BorderPane borderPane = new BorderPane();
		HBox box = new HBox();
		box.getChildren().addAll(username, user);
		HBox box2 = new HBox();
		box2.getChildren().addAll(password, pass);
		HBox box4 = new HBox();
		box4.getChildren().addAll(l,login);

		VBox box3 = new VBox();
		box3.getChildren().addAll(box, box2, box4);
		box3.setSpacing(10);
		box3.setPadding(new Insets(160, 0, 0, 400));
		//borderPane
				//.setStyle("-fx-background-color:linear-gradient(#306DAE,#3E8AC6)");

		borderPane.setCenter(box3);
		Image image=new Image("file:background4.jpg");
		BackgroundImage myBI= new BackgroundImage(image,
			       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
			        BackgroundSize.DEFAULT);

		borderPane.setBackground(new Background(myBI));

		return borderPane;
	}

	public BorderPane adminFunction() {

		TableView<Artist> table2 = new TableView<>();

		BorderPane adminBorderPane = new BorderPane();
		VBox leftadmin = new VBox();
		leftadmin.getChildren().addAll(songLabel, inputSong, artistLabel,
				inputAtrist, albumLabel, inputAlbum, durationLabel,
				inputDuratin, submit,update, delete, deletealbumartist, notice);
		//leftadmin
			//	.setStyle("-fx-background-color:linear-gradient(#1C3C61,#006191)");
		leftadmin.setPadding(new Insets(10));
		leftadmin.setSpacing(10);
		adminBorderPane.setLeft(leftadmin);

		VBox right = new VBox();
		playselect = new Button("Play");
		playselect.setMinWidth(100);
		adminallmusic = new Button("All Music");
		adminallmusic.setMinWidth(100);
		adminalbum = new Button("Album");
		adminalbum.setMinWidth(100);
		adminartist = new Button("Artist");
		adminartist.setMinWidth(100);
		adminload = new Button("Load");
		adminload.setMinWidth(100);
		right.getChildren().addAll(playselect, adminallmusic, adminalbum,
				adminartist, adminload);
		right.setSpacing(10);
		//right.setStyle("-fx-background-color:linear-gradient(#1C3C61,#006191)");
		right.setPadding(new Insets(10, 10, 0, 20));
		adminBorderPane.setRight(right);

		adminfunctionflag = 1;
		allmusictableview();

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				song_name = inputSong.getText();
				artistname = inputAtrist.getText();
				albumname = inputAlbum.getText();
				duration = inputDuratin.getText();
				if (!song_name.equals("") && !artistname.equals("")
						&& !albumname.equals("") && !duration.equals("")) {
					String sql;

					try {

						Statement myStmt = (Statement) con.createStatement();
						ResultSet myRs;
						myRs = myStmt.executeQuery("select * from artist");
						int flagar = 0;
						int flagal = 0;
						int arid = 0;
						int alid = 0;
						while (myRs.next()) {
							String n = myRs.getString("artist");
							arid = myRs.getInt("artist_id");
							if (artistname.equals(n)) {
								flagar = 1;
								break;
							}
						}

						myRs = myStmt.executeQuery("select * from album");
						while (myRs.next()) {
							String n = myRs.getString("album");
							alid = myRs.getInt("album_id");
							if (albumname.equals(n)) {
								flagal = 1;
								break;
							}
						}

						if (flagar == 0 && flagal == 0) {

							sql = "insert into artist" + "(artist_id,artist)"
									+ "values('" + (arid + 1) + "','"
									+ artistname + "')";

							myStmt.executeUpdate(sql);

							sql = "insert into album" + "(album_id,album)"
									+ "values('" + (alid + 1) + "','"
									+ albumname + "')";

							myStmt.executeUpdate(sql);

							sql = "insert into music_list"
									+ "(song_name,artist,album,duration)"
									+ "values('" + song_name + "','"
									+ (arid + 1) + "','" + (alid + 1) + "','"
									+ duration + "')";

							myStmt.executeUpdate(sql);

						} else if (flagar == 0 && flagal == 1) {

							sql = "insert into artist" + "(artist_id,artist)"
									+ "values('" + (arid + 1) + "','"
									+ artistname + "')";

							myStmt.executeUpdate(sql);

							sql = "insert into music_list"
									+ "(song_name,artist,album,duration)"
									+ "values('" + song_name + "','"
									+ (arid + 1) + "','" + alid + "','"
									+ duration + "')";

							myStmt.executeUpdate(sql);

						}

						else if (flagar == 1 && flagal == 0) {

							sql = "insert into album" + "(album_id,album)"
									+ "values('" + (alid + 1) + "','"
									+ albumname + "')";

							myStmt.executeUpdate(sql);

							sql = "insert into music_list"
									+ "(song_name,artist,album,duration)"
									+ "values('" + song_name + "','" + (arid)
									+ "','" + (alid + 1) + "','" + duration
									+ "')";

							myStmt.executeUpdate(sql);
						}

						else if (flagar == 1 && flagal == 1) {

							sql = "insert into music_list"
									+ "(song_name,artist,album,duration)"
									+ "values('" + song_name + "','" + (arid)
									+ "','" + (alid) + "','" + duration + "')";

							myStmt.executeUpdate(sql);

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// notice.setText("Music added successfully");

					inputSong.setText("");

					inputAtrist.setText("");

					inputAlbum.setText("");

					inputDuratin.setText("");
					// root.setCenter(adminFunction());
					adminfunctionflag = 1;
					allmusictableview();
					root.setCenter(table);
					root.setLeft(leftadmin);
					root.setRight(right);
					root.setBackground(new Background(myBI));
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Music added successfully!");
					alert.showAndWait();

				} else {
					// notice.setText("Enter all the fields");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Enter all fields!");

					alert.showAndWait();

				}
			}
		});

		playselect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				deletesong=null;
				System.out.println(source);
				if (source != null) {
					boolean playing = mediaPlayer.getStatus().equals(
							Status.PLAYING);
					if (playing) {
						mediaPlayer.stop();
					} else {
						mediaPlayer.stop();
					}
					media = new Media(source);
					mediaPlayer = new MediaPlayer(media);

					root.setBottom(new MediaBar(mediaPlayer));
					
					mediaPlayer.play();
					root.setBackground(new Background(myBI));
				}

			}
		});
		adminalbum.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				adminalbumbuttonflag = 1;
				adminartistbuttonflag = 0;
				System.out.println("album");
				TableView<Album> table1 = new TableView<>();

				final ObservableList<Album> albumList = FXCollections
						.observableArrayList();

				TableColumn column3 = new TableColumn("Serial");
				column3.setMinWidth(250);
				column3.setCellValueFactory(new PropertyValueFactory<>(
						"album_id"));

				TableColumn column4 = new TableColumn("Album name");
				column4.setMinWidth(250);
				column4.setCellValueFactory(new PropertyValueFactory<>(
						"album_name"));

				table1.getColumns().addAll(column3, column4);

				try {
					ResultSet rst = con.createStatement().executeQuery(
							"select * from album");
					while (rst.next()) {

						albumList.add(new Album(rst.getInt("album_id"), rst
								.getString("album")));
					}
					table1.setItems(albumList);

					rst.close();

				} catch (Exception e) {
				}
				table1.getSelectionModel()
						.selectedItemProperty()
						.addListener((observableValue, oldValue, newValue) -> {
							// Check whether item is selected and set value of
							// selected
							// item to Label

								if (table1.getSelectionModel()
										.getSelectedItem() != null) {
									Album nm = table1.getSelectionModel()
											.getSelectedItem();
									albumid = nm.getAlbum_id();
									deletealbum = nm.getAlbum_name();
									System.out.println(albumid);

								}
							});
				root.setCenter(table1);
				root.setLeft(leftadmin);
				root.setRight(right);
				root.setBackground(new Background(myBI));

			}
		});
		adminartist.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("artist");
				adminartistbuttonflag = 1;
				adminalbumbuttonflag = 0;
				TableView<Artist> table2 = new TableView<>();
				final ObservableList<Artist> artistList = FXCollections
						.observableArrayList();

				TableColumn column1 = new TableColumn("Serial");
				column1.setMinWidth(250);
				column1.setCellValueFactory(new PropertyValueFactory<>(
						"artist_id"));

				TableColumn column2 = new TableColumn("Atrist name");
				column2.setMinWidth(250);
				column2.setCellValueFactory(new PropertyValueFactory<>(
						"artist_name"));

				table2.getColumns().addAll(column1, column2);

				try {
					ResultSet rst = con.createStatement().executeQuery(
							"select * from artist");
					while (rst.next()) {

						artistList.add(new Artist(rst.getInt("artist_id"), rst
								.getString("artist")));
					}

					table2.setItems(artistList);

					rst.close();

				} catch (Exception e) {
				}
				table2.getSelectionModel()
						.selectedItemProperty()
						.addListener((observableValue, oldValue, newValue) -> {
							// Check whether item is selected and set value of
							// selected
							// item to Label

								if (table2.getSelectionModel()
										.getSelectedItem() != null) {
									Artist nm = table2.getSelectionModel()
											.getSelectedItem();
									artistid = nm.getArtist_id();
									deleteartist = nm.getArtist_name();
									System.out.println(artistid);

								}
							});

				root.setCenter(table2);
				root.setRight(right);
				root.setLeft(leftadmin);
				root.setBackground(new Background(myBI));

			}
		});

		adminallmusic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("allmusic");
				adminfunctionflag = 1;
				allmusictableview();
				root.setLeft(leftadmin);
				root.setCenter(table);
				root.setRight(right);
				root.setBackground(new Background(myBI));
			}
		});
		
		
		
		update.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				update_song_name=inputSong.getText().toString();
				update_duration=inputDuratin.getText().toString();
				update_album_name=inputAlbum.getText().toString();
				update_artist_name=inputAtrist.getText().toString();
				//String update_music_list="update music_list set song_name='"+update_song_name+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";
			
				//String update_album_artist="select artist,album from music_list where song_id='"+update_song_id+"'";
				ResultSet set=null;
				try {

					String sql;
					Connection con=SqliteConnection.Connector();
					Statement myStmt = (Statement) con.createStatement();
					ResultSet myRs;
					myRs = myStmt.executeQuery("select * from artist");
					int flagar = 0;
					int flagal = 0;
					int arid = 0;
					int alid = 0;
					while (myRs.next()) {
						String n = myRs.getString("artist");
						arid = myRs.getInt("artist_id");
						if (update_artist_name.equals(n)) {
							flagar = 1;
							break;
						}
					}

					myRs = myStmt.executeQuery("select * from album");
					while (myRs.next()) {
						String n = myRs.getString("album");
						alid = myRs.getInt("album_id");
						if (update_album_name.equals(n)) {
							flagal = 1;
							break;
						}
					}

					if (flagar == 0 && flagal == 0) {

						sql = "insert into artist" + "(artist_id,artist)"
								+ "values('" + (arid + 1) + "','"
								+ update_artist_name + "')";

						myStmt.executeUpdate(sql);

						sql = "insert into album" + "(album_id,album)"
								+ "values('" + (alid + 1) + "','"
								+ update_album_name + "')";

						myStmt.executeUpdate(sql);

						sql = "update music_list set song_name='"+update_song_name+"',artist='"+(arid+1)+"',album='"+(alid+1)+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";

						myStmt.executeUpdate(sql);

					} else if (flagar == 0 && flagal == 1) {

						sql = "insert into artist" + "(artist_id,artist)"
								+ "values('" + (arid + 1) + "','"
								+ update_artist_name + "')";

						myStmt.executeUpdate(sql);

						sql = "update music_list set song_name='"+update_song_name+"',artist='"+(arid+1)+"',album='"+alid+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";

						myStmt.executeUpdate(sql);

					}

					else if (flagar == 1 && flagal == 0) {

						sql = "insert into album" + "(album_id,album)"
								+ "values('" + (alid + 1) + "','"
								+ update_album_name + "')";

						myStmt.executeUpdate(sql);

						sql ="update music_list set song_name='"+update_song_name+"',artist='"+arid+"',album='"+(alid+1)+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";

						myStmt.executeUpdate(sql);
					}

					else if (flagar == 1 && flagal == 1) {

						sql ="update music_list set song_name='"+update_song_name+"',artist='"+arid+"',album='"+alid+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";

						myStmt.executeUpdate(sql);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				inputSong.setText("");

				inputAtrist.setText("");

				inputAlbum.setText("");

				inputDuratin.setText("");
				allmusictableview();
				root.setCenter(table);
				root.setLeft(leftadmin);
				root.setRight(right);
				
				Image image=new Image("file:background4.jpg");
				BackgroundImage myBI= new BackgroundImage(image,
					       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
					        BackgroundSize.DEFAULT);

				root.setBackground(new Background(myBI));
			}
			
		});
		
		/*update.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			update_song_name=inputSong.getText().toString();
			update_duration=inputDuratin.getText().toString();
			update_album_name=inputAlbum.getText().toString();
			update_artist_name=inputAtrist.getText().toString();
			String update_music_list="update music_list set song_name='"+update_song_name+"',duration='"+update_duration+"' where song_id='"+update_song_id+"'";
		
			String update_album_artist="select artist,album from music_list where song_id='"+update_song_id+"'";
			ResultSet set=null;
			try {
				con.createStatement().executeUpdate(update_music_list);
				set=con.createStatement().executeQuery(update_album_artist);
				
				
				while (set.next()) {
					update_album_id=set.getInt("album");
					update_artist_id=set.getInt("artist");
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String update_album="update album set album='"+update_album_name+"' where album_id='"+update_album_id+"'";
			String update_artist="update artist set artist='"+update_artist_name+"' where artist_id='"+update_artist_id+"'";

			try{
				con.createStatement().executeUpdate(update_artist);
				con.createStatement().executeUpdate(update_album);
				}catch(Exception e){
					


				
			}
			inputSong.setText("");

			inputAtrist.setText("");

			inputAlbum.setText("");

			inputDuratin.setText("");
			allmusictableview();
			root.setCenter(table);
			root.setLeft(leftadmin);
			root.setRight(right);
			
			Image image=new Image("file:background4.jpg");
			BackgroundImage myBI= new BackgroundImage(image,
				       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				        BackgroundSize.DEFAULT);

			root.setBackground(new Background(myBI));
		}
		
	});*/

		deletealbumartist.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (adminalbumbuttonflag == 1) {
					//adminalbumbuttonflag = 0;

					if (deletealbum != null) {
						Alert alert = new Alert(AlertType.CONFIRMATION,
								"Delete " + deletealbum + " ?", ButtonType.YES,
								ButtonType.NO, ButtonType.CANCEL);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES) {
							// do stuff
							String q = "delete from album where album='"
									+ deletealbum + "'";

							try {
								con.createStatement().executeUpdate(q);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						//adminalbumbuttonflag = 1;
						System.out.println("album");
						TableView<Album> table1 = new TableView<>();

						final ObservableList<Album> albumList = FXCollections
								.observableArrayList();

						TableColumn column3 = new TableColumn("Serial");
						column3.setMinWidth(250);
						column3.setCellValueFactory(new PropertyValueFactory<>(
								"album_id"));

						TableColumn column4 = new TableColumn("Album name");
						column4.setMinWidth(250);
						column4.setCellValueFactory(new PropertyValueFactory<>(
								"album_name"));

						table1.getColumns().addAll(column3, column4);

						try {
							ResultSet rst = con.createStatement().executeQuery(
									"select * from album");
							while (rst.next()) {

								albumList.add(new Album(rst.getInt("album_id"),
										rst.getString("album")));
							}
							table1.setItems(albumList);

							rst.close();

						} catch (Exception e) {
						}
						table1.getSelectionModel()
								.selectedItemProperty()
								.addListener(
										(observableValue, oldValue, newValue) -> {
											// Check whether item is selected
											// and set value of selected
											// item to Label

											if (table1.getSelectionModel()
													.getSelectedItem() != null) {
												Album nm = table1
														.getSelectionModel()
														.getSelectedItem();
												albumid = nm.getAlbum_id();
												deletealbum = nm
														.getAlbum_name();
												System.out.println(albumid);

											}
										});
						root.setCenter(table1);
						root.setLeft(leftadmin);
						root.setRight(right);
						root.setBackground(new Background(myBI));
						deletealbum=null;
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Select album!");
						alert.showAndWait();
					}

				}

				else if (adminartistbuttonflag == 1) {
					//adminartistbuttonflag = 0;

					if (deleteartist != null) {
						Alert alert = new Alert(AlertType.CONFIRMATION,
								"Delete " + deleteartist + " ?",
								ButtonType.YES, ButtonType.NO,
								ButtonType.CANCEL);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES) {
							// do stuff
							String q = "delete from artist where artist='"
									+ deleteartist + "'";

							try {
								con.createStatement().executeUpdate(q);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}

						// artistbutton call
						System.out.println("artist");
						//adminartistbuttonflag = 1;
						TableView<Artist> table2 = new TableView<>();
						final ObservableList<Artist> artistList = FXCollections
								.observableArrayList();

						TableColumn column1 = new TableColumn("Serial");
						column1.setMinWidth(250);
						column1.setCellValueFactory(new PropertyValueFactory<>(
								"artist_id"));

						TableColumn column2 = new TableColumn("Atrist name");
						column2.setMinWidth(250);
						column2.setCellValueFactory(new PropertyValueFactory<>(
								"artist_name"));

						table2.getColumns().addAll(column1, column2);

						try {
							ResultSet rst = con.createStatement().executeQuery(
									"select * from artist");
							while (rst.next()) {

								artistList.add(new Artist(rst
										.getInt("artist_id"), rst
										.getString("artist")));
							}

							table2.setItems(artistList);

							rst.close();

						} catch (Exception e) {
						}
						table2.getSelectionModel()
								.selectedItemProperty()
								.addListener(
										(observableValue, oldValue, newValue) -> {
											// Check whether item is selected
											// and set value of selected
											// item to Label

											if (table2.getSelectionModel()
													.getSelectedItem() != null) {
												Artist nm = table2
														.getSelectionModel()
														.getSelectedItem();
												artistid = nm.getArtist_id();
												deleteartist = nm
														.getArtist_name();
												System.out.println(artistid);

											}
										});

						root.setCenter(table2);
						root.setRight(right);
						root.setLeft(leftadmin);
						root.setBackground(new Background(myBI));
						deleteartist=null;
					} 
					else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Select artist!");
						alert.showAndWait();
					}

				} else {

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Select Album or Artist!");
					alert.showAndWait();

				}

				// root.setCenter(adminFunction());

			}
		});
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (deletesong != null) {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Delete "
							+ deletesong + " ?", ButtonType.YES, ButtonType.NO,
							ButtonType.CANCEL);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						
						try{
							
							Connection con=SqliteConnection.Connector();
							PreparedStatement myStmt1 = con.prepareStatement("delete from music_list where song_name=? and artist=? and album=?");
							myStmt1.setString(1, deletesong);
							myStmt1.setInt(2, artist_id_playlist);
							myStmt1.setInt(3, album_id_playlist);
							myStmt1.executeUpdate();
							
						}catch(Exception e){
							
						}
					}
					adminfunctionflag = 1;
					allmusictableview();
					// root.setCenter(adminFunction());
					root.setCenter(table);
					root.setRight(right);
					root.setLeft(leftadmin);
					root.setBackground(new Background(myBI));
					deletesong=null;
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Select music!");
					alert.showAndWait();
				}

			}
		});

		adminload.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("allmusic");
				System.out.println(albumid);

				// loadalbumflag=1;
				// allmusictableview();
				if (adminalbumbuttonflag == 1) {
					if(albumid!=0){
						
						adminalbumbuttonflag = 0;
						loadalbumflag = 1;
						allmusictableview();
						root.setLeft(leftadmin);
						root.setCenter(table);
						root.setRight(right);
						root.setBackground(new Background(myBI));
						
					}
					else{
						
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning load button");
						alert.setHeaderText(null);
						alert.setContentText("Select album or artist");
						alert.showAndWait();
						
					}
					

				} else if (adminartistbuttonflag == 1) {
					
					if(artistid!=0){
						
						adminartistbuttonflag = 0;
						loadartistflag = 1;
						allmusictableview();
						root.setLeft(leftadmin);
						root.setCenter(table);
						root.setRight(right);
						root.setBackground(new Background(myBI));
						
					}
					
					else{
						
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning load button");
						alert.setHeaderText(null);
						alert.setContentText("Select album or artist");
						alert.showAndWait();
						
					}
					
				}
				else{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning load button");
					alert.setHeaderText(null);
					alert.setContentText("Select album or artist");
					alert.showAndWait();
				}

				
				

			}
		});

		//adminBorderPane
				//.setStyle("-fx-background-color:linear-gradient(#1C3C61,#006191)");

		adminBorderPane.setCenter(table);
		Image image=new Image("file:background4.jpg");
		BackgroundImage myBI= new BackgroundImage(image,
			       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
			        BackgroundSize.DEFAULT);

		adminBorderPane.setBackground(new Background(myBI));

		return adminBorderPane;

	}

	public void allmusictableview() {
		table = new TableView<>();
		final ObservableList<AllMusic> musicList = FXCollections
				.observableArrayList();
		TableColumn column1 = new TableColumn("Track");
		column1.setMinWidth(160);
		column1.setCellValueFactory(new PropertyValueFactory<>("song_name"));

		TableColumn column2 = new TableColumn("Artist");
		column2.setMinWidth(160);
		column2.setCellValueFactory(new PropertyValueFactory<>("artist"));

		TableColumn column3 = new TableColumn("Album");
		column3.setMinWidth(160);
		column3.setCellValueFactory(new PropertyValueFactory<>("album"));

		TableColumn column4 = new TableColumn("Duration");
		column4.setMinWidth(120);
		column4.setCellValueFactory(new PropertyValueFactory<AllMusic, String>(
				"duration"));

		TableColumn column5 = new TableColumn("Total Played");
		column5.setMinWidth(130);
		column5.setCellValueFactory(new PropertyValueFactory<AllMusic, String>(
				"hit"));
		
		
		TableColumn column6=new TableColumn("Song id");
		column6.setMinWidth(20);
		column6.setCellValueFactory(new PropertyValueFactory<AllMusic,String>("song_id"));

		table.getColumns().addAll(column6,column1, column2, column3, column4, column5);

		try {
			ResultSet rst = null;
			if (loadalbumflag == 1) {
				rst = con.createStatement().executeQuery(
						"select * from music_list where album='" + albumid
								+ "'");
				loadalbumflag = 0;

			} else if (loadartistflag == 1) {
				rst = con.createStatement().executeQuery(
						"select * from music_list where artist='" + artistid
								+ "'");
				loadartistflag = 0;

			}

			else if (loadplaylistflag == 1) {
				rst = con.createStatement().executeQuery(
						"select * from playlist");
				loadplaylistflag = 0;

			} else if (adminfunctionflag == 1) {
				rst = con.createStatement().executeQuery(
						"select * from music_list");
				// loadallmusicflag=0;
			}

			while (rst.next()) {
				String art = rst.getString("artist");
				String alb = rst.getString("album");
				int i = Integer.parseInt(art);
				int j = Integer.parseInt(alb);
				ResultSet r = con.createStatement().executeQuery(
						"select * from artist where artist_id='" + i + "'");

				while (r.next()) {
					art = r.getString("artist");

				}

				r = con.createStatement().executeQuery(
						"select * from album where album_id='" + j + "'");
				while (r.next()) {
					alb = r.getString("album");

				}

				musicList.add(new AllMusic(rst.getInt("song_id"),rst.getString("song_name"), art,
						alb, rst.getString("duration"), rst.getString("hit")));
			}
			table.setItems(musicList);
			albumid=0;
			artistid=0;
			rst.close();

		} catch (Exception e) {
		}

		table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
					// Check whether item is selected and set value of selected
					// item to
					// Label
						if (table.getSelectionModel().getSelectedItem() != null) {
							
							
							AllMusic m = table.getSelectionModel().getSelectedItem();
							
							inputSong.setText(m.getSong_name());
							inputAtrist.setText(m.getArtist());
							inputAlbum.setText(m.getAlbum());
							inputDuratin.setText(m.getDuration());
							
							String sn = m.getSong_name().toString();
							song_name_playlist = m.getSong_name().toString();
							duration_playlist = m.getDuration().toString();

							update_song_id=m.getSong_id();
							
							deletesong = m.getSong_name().toString();

							System.out.println(sn);
							try {
								Connection con = SqliteConnection.Connector();
								PreparedStatement myStmt = (PreparedStatement) con
										.prepareStatement("select * from music_list where song_name=?");
								myStmt.setString(1, sn);
								ResultSet r = myStmt.executeQuery();

								while (r.next()) {
									song_id_update = r.getInt("song_id");
									song_hit_update = r.getInt("hit");
									System.out.println(song_hit_update);
									song_hit_update = song_hit_update + 1;

									artist_id_playlist = r.getInt("artist");
									album_id_playlist = r.getInt("album");
									hit_playlist = r.getInt("hit");

								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							/*AllMusic m = table.getSelectionModel().getSelectedItem();
							inputSong.setText(m.getSong_name());
							inputAtrist.setText(m.getArtist());
							inputAlbum.setText(m.getAlbum());
							inputDuratin.setText(m.getDuration());
							int arid = 0;
							int alid = 0;
							
							String sn=m.getSong_name().toString();
							song_name_playlist = m.getSong_name().toString();
							duration_playlist = m.getDuration().toString();
							String ar=m.getArtist().toString();
							String al=m.getAlbum().toString();
							try {

								String sql;
								Connection con=SqliteConnection.Connector();
								Statement myStmt = (Statement) con.createStatement();
								ResultSet myRs;
								myRs = myStmt.executeQuery("select * from artist");

								while (myRs.next()) {
									String n = myRs.getString("artist");
									arid = myRs.getInt("artist_id");
									if (ar.equals(n)) {
										//flagar = 1;
										break;
									}
								}

								myRs = myStmt.executeQuery("select * from album");
								while (myRs.next()) {
									String n = myRs.getString("album");
									alid = myRs.getInt("album_id");
									if (al.equals(n)) {
										//flagal = 1;
										break;
									}
								}
								PreparedStatement myStmt1 =con.prepareStatement("select * from music_list where song_name=? and artist=? and album=?");
								myStmt1.setString(1, sn);
								myStmt1.setInt(2, arid);
								myStmt1.setInt(3, alid);
								ResultSet r = myStmt1.executeQuery();
								
								while(r.next()){
									song_id_update=r.getInt("song_id");
									song_name_playlist=r.getString("song_name");
									deletesong=r.getString("song_name");
									artist_id_playlist=r.getInt("artist");
									album_id_playlist=r.getInt("album");
									duration_playlist=r.getString("duration");
									hit_playlist=r.getInt("hit");
									
									song_hit_update=r.getInt("hit");
									System.out.println(song_hit_update);
									song_hit_update=song_hit_update+1;

									
									

								}
							}catch(Exception e){
								e.printStackTrace();
							}*/
							//File file = new File("D:/server/"+ m.getSong_name().toString() + ".mp3");
							File file=new File("//Anon-Laptop/Users/Public/"+m.getSong_name().toString()+"_"+m.getArtist().toString()+"_"+m.getAlbum().toString()+ ".mp3");
							source = file.toURI().toString();
							System.out.println(source);
						}
					});
	}

}
