package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;

public class Listener {
	public Connection con;
	public Media media = null;
	public MediaPlayer mediaPlayer = null;
	public static String source, src,sr;
	// left listener
	public Button allMusic, album, artist,loadAlbum,loadPlaylist,download;

	// right listener
	public Button playselect,addPlaylist,deletePlaylist;

	int albumid,artistid;
	int song_id_update=0,song_hit_update=0;
	int loadalbumflag=0,loadartistflag=0;
	int loadallmusicflag=1;


	int artistbuttonflag=0,albumbuttonflag=0;
	int adminfunctionflag=0;
	int loadplaylistflag=0;

	InputStream input = null;
	OutputStream output = null;

	String song_name_playlist,duration_playlist,song_download;
	int artist_id_playlist,album_id_playlist,hit_playlist;

	TableView<AllMusic> table;
	Stage window;
	public Listener(){
		window=new Stage();
		con=SqliteConnection.Connector();


			BorderPane root = new BorderPane();
			VBox left = new VBox();

			File f=new File("D:/server/Tamak Pata.mp3");
			src = f.toURI().toString();
			media=new Media(src);
			mediaPlayer=new MediaPlayer(media);
			mediaPlayer.play();
			mediaPlayer.stop();
			root.setBottom(new MediaBar(mediaPlayer));
			allMusic = new Button("All Music");
			album = new Button("Album");
			artist = new Button("Artist");
			loadAlbum=new Button("Load");
			loadPlaylist=new Button("Playlist");
			download=new Button("Download");

			allMusic.setPrefWidth(100);
			album.setPrefWidth(100);
			artist.setPrefWidth(100);
			loadAlbum.setPrefWidth(100);
			loadPlaylist.setPrefWidth(100);
			left.setSpacing(10);
			left.setPadding(new Insets(20));
			//left.setStyle("-fx-background-color:linear-gradient(#1C3C61,#006191)");
			left.getChildren().addAll(allMusic, album, artist,loadAlbum,loadPlaylist);

			root.setLeft(left);
			VBox right = new VBox();
			playselect = new Button("Play");
			playselect.setMinWidth(100);
			addPlaylist = new Button("Add Playlist");
			addPlaylist.setMinWidth(100);
			deletePlaylist = new Button("Delete From Playlist");
			deletePlaylist.setMinWidth(100);
			right.getChildren().addAll(playselect,addPlaylist,deletePlaylist,download);
			right.setSpacing(10);
			//right.setStyle("-fx-background-color:linear-gradient(#1C3C61,#006191)");
			right.setPadding(new Insets(10, 10, 0, 20));
			root.setRight(right);

			allmusictableview();

			loadPlaylist.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0){

					loadplaylistflag=1;
					allmusictableview();
					root.setLeft(left);
					root.setCenter(table);
					root.setRight(right);


				}
			});

			addPlaylist.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0){
					try{
						Connection con=SqliteConnection.Connector();
						Statement myStmt =con.createStatement();

						String sql = "insert into playlist" + "(song_name,artist,album,duration,hit)" + "values('"
								+ song_name_playlist + "','" + artist_id_playlist + "','" + album_id_playlist + "','" + duration_playlist + "','" + hit_playlist + "')";

						myStmt.executeUpdate(sql);

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Song added to playlist!");
						alert.showAndWait();

						loadplaylistflag=1;
						allmusictableview();
						root.setCenter(table);
						root.setRight(right);
						root.setLeft(left);


					}catch(Exception e){
						e.printStackTrace();

					}

				}
			});

			download.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0){

					if(song_download!=null){

						try {
				    		//input = new FileInputStream("D:/server/"+song_download+".mp3");
							input=new FileInputStream("//Anon-Laptop/Users/Public/"+song_download+".mp3");
				    		output = new FileOutputStream("C:/Users/Anon/Downloads/downloadmusic/"+song_download+".mp3");
				    		byte[] buf = new byte[15350];
				    		int bytesRead;
				    		while ((bytesRead = input.read(buf)) > 0) {
				    			output.write(buf, 0, bytesRead);
				    		}
				    		Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information");
							alert.setHeaderText(null);
							alert.setContentText("Song downloaded successfully");
							alert.showAndWait();
							song_download=null;
				    		input.close();
				    		output.close();
				    	} catch(Exception e){

				    	}

					}
					else{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Song not selected");
						alert.showAndWait();
					}




				}
			});
			deletePlaylist.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0){

					if (song_name_playlist != null) {
						Alert alert = new Alert(AlertType.CONFIRMATION, "Delete "
								+ song_name_playlist + " ?", ButtonType.YES, ButtonType.NO,
								ButtonType.CANCEL);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES) {
							try{
								
								Connection con=SqliteConnection.Connector();
								PreparedStatement myStmt1 =con.prepareStatement("delete from playlist where song_name=? and artist=? and album=?");
								myStmt1.setString(1, song_name_playlist);
								myStmt1.setInt(2, artist_id_playlist);
								myStmt1.setInt(3, album_id_playlist);
								myStmt1.executeUpdate();
								
							}catch(Exception e){
								
							}
						}
						loadplaylistflag = 1;
						allmusictableview();
						// root.setCenter(adminFunction());
						root.setCenter(table);
						root.setRight(right);
						root.setLeft(left);
						song_name_playlist=null;
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Select music!");
						alert.showAndWait();
					}


				}
			});

			playselect.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					//song_name_playlist=null;
					System.out.println(source);
					if(source!=null){
						boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
						if(playing){
							mediaPlayer.stop();
						}
						else{
							mediaPlayer.stop();
						}
						media = new Media(source);
						mediaPlayer = new MediaPlayer(media);

						root.setBottom(new MediaBar(mediaPlayer));

						mediaPlayer.play();

						Statement st=null;
						try{

							Connection con=SqliteConnection.Connector();
							st=con.createStatement();
							PreparedStatement myStmt1 =con.prepareStatement("UPDATE music_list SET hit='"+song_hit_update+"' where song_name=? and artist=? and album=?");
							myStmt1.setString(1, song_name_playlist);
							myStmt1.setInt(2, artist_id_playlist);
							myStmt1.setInt(3, album_id_playlist);
							myStmt1.executeUpdate();
							//String sql = "UPDATE music_list SET hit = '"+song_hit_update+"' WHERE song_id='"+song_id_update+"'";
							//String sql = "UPDATE music_list SET hit = '"+song_hit_update+"' WHERE song_name='"+song_name_playlist+"' and artist='"+artist_id_playlist+"' and album='"+album_id_playlist+"'";
							//st.executeUpdate(sql);
							loadallmusicflag=1;
							//root.setCenter(listenerFunction());

						}catch(Exception e){
							e.printStackTrace();
						}

					}
				}
			});
			album.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					albumbuttonflag=1;
					artistbuttonflag=0;
					System.out.println("album");
					TableView<Album> table1=new TableView<>();

					final ObservableList<Album> albumList=FXCollections.observableArrayList();

					TableColumn column3=new TableColumn("Serial");
					column3.setMinWidth(250);
					column3.setCellValueFactory(new PropertyValueFactory<>("album_id"));

					TableColumn column4=new TableColumn("Album name");
					column4.setMinWidth(250);
					column4.setCellValueFactory(new PropertyValueFactory<>("album_name"));

					table1.getColumns().addAll(column3,column4);


					try{
						ResultSet rst=con.createStatement().executeQuery("select * from album");
						while(rst.next()){

							albumList.add(new Album(rst.getInt("album_id"),rst.getString("album")));
						}
						table1.setItems(albumList);

						rst.close();

					}catch(Exception e){
					}
					table1.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
						// Check whether item is selected and set value of selected
						// item to Label

						if (table1.getSelectionModel().getSelectedItem() != null) {
							Album nm = table1.getSelectionModel().getSelectedItem();
							albumid=nm.getAlbum_id();
							System.out.println(albumid);

						}
					});
					root.setCenter(table1);
					root.setLeft(left);
					root.setRight(right);

				}
			});



			artist.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("artist");
					artistbuttonflag=1;
					albumbuttonflag=0;
					TableView<Artist> table2=new TableView<>();
					final ObservableList<Artist> artistList=FXCollections.observableArrayList();

					TableColumn column1=new TableColumn("Serial");
					column1.setMinWidth(250);
					column1.setCellValueFactory(new PropertyValueFactory<>("artist_id"));

					TableColumn column2=new TableColumn("Atrist name");
					column2.setMinWidth(250);
					column2.setCellValueFactory(new PropertyValueFactory<>("artist_name"));


					table2.getColumns().addAll(column1,column2);

					try{
						ResultSet rst=con.createStatement().executeQuery("select * from artist");
						while(rst.next()){

							artistList.add(new Artist(rst.getInt("artist_id"),rst.getString("artist")));
						}

						table2.setItems(artistList);

						rst.close();

					}catch(Exception e){
					}
					table2.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
						// Check whether item is selected and set value of selected
						// item to Label

						if (table2.getSelectionModel().getSelectedItem() != null) {
							Artist nm = table2.getSelectionModel().getSelectedItem();
							artistid=nm.getArtist_id();
							System.out.println(artistid);

						}
					});

					root.setCenter(table2);
					root.setRight(right);
					root.setLeft(left);


				}
			});

			allMusic.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("allmusic");
					loadallmusicflag=1;
					allmusictableview();
					root.setLeft(left);
					root.setCenter(table);
					root.setRight(right);
				}
			});

			loadAlbum.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("loadalbumartist");
					System.out.println(albumid);

					/*loadalbumflag=1;
					allmusictableview();*/
					if(albumbuttonflag==1){

						if(albumid!=0){
							albumbuttonflag=0;
							loadalbumflag=1;
							allmusictableview();
							root.setLeft(left);
							root.setCenter(table);
							root.setRight(right);

						}
						else{

							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Warning load button");
							alert.setHeaderText(null);
							alert.setContentText("Select album or artist");
							alert.showAndWait();

						}


					}

					else if(artistbuttonflag==1){

						if(artistid!=0){

							artistbuttonflag=0;
							loadartistflag=1;
							allmusictableview();
							root.setLeft(left);
							root.setCenter(table);
							root.setRight(right);

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
						alert.setTitle("Warning loadbutton");
						alert.setHeaderText(null);
						alert.setContentText("Select album or artist");
						alert.showAndWait();

					}



				}
			});

			root.setCenter(table);
			//listenerBorderPane.setBottom(null);
			Image image=new Image("file:background4.jpg");
			BackgroundImage myBI= new BackgroundImage(image,
				       BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				        BackgroundSize.DEFAULT);

			root.setBackground(new Background(myBI));
			Scene scene=new Scene(root,1100,450);
			window.setScene(scene);
			window.show();

	}
	public void allmusictableview(){
		table = new TableView<>();
		final ObservableList<AllMusic> musicList = FXCollections.observableArrayList();
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
		column4.setCellValueFactory(new PropertyValueFactory<AllMusic, String>("duration"));

		TableColumn column5=new TableColumn("Total Played");
		column5.setMinWidth(130);
		column5.setCellValueFactory(new PropertyValueFactory<AllMusic,String>("hit"));

		

		TableColumn column6=new TableColumn("Track id");
		column6.setMaxWidth(0);
		column6.setCellValueFactory(new PropertyValueFactory<AllMusic,String>("song_id"));
		
		table.getColumns().addAll(column1, column2, column3, column4,column5);

		try {
			ResultSet rst = null;
			if(loadalbumflag==1){
				rst = con.createStatement().executeQuery("select * from music_list where album='"+albumid+"'");
				loadalbumflag=0;

			}
			else if(loadartistflag==1){
				rst = con.createStatement().executeQuery("select * from music_list where artist='"+artistid+"'");
				loadartistflag=0;

			}

			else if(loadplaylistflag==1){
				rst = con.createStatement().executeQuery("select * from playlist");
				loadplaylistflag=0;

			}
			else if(loadallmusicflag==1){
				rst = con.createStatement().executeQuery("select * from music_list");
				//loadallmusicflag=0;
				//adminfunctionflag=0;
			}


			while (rst.next()) {
				String art = rst.getString("artist");
				String alb = rst.getString("album");
				int i = Integer.parseInt(art);
				int j = Integer.parseInt(alb);
				ResultSet r = con.createStatement().executeQuery("select * from artist where artist_id='" + i + "'");

				while (r.next()) {
					art = r.getString("artist");

				}

				r = con.createStatement().executeQuery("select * from album where album_id='" + j + "'");
				while (r.next()) {
					alb = r.getString("album");

				}

				musicList.add(new AllMusic(rst.getInt("song_id"),rst.getString("song_name"), art, alb, rst.getString("duration"), rst.getString("hit")));
			}
			table.setItems(musicList);

			albumid=0;
			artistid=0;

			rst.close();

		} catch (Exception e) {
		}

		table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			// Check whether item is selected and set value of selected item to
			// Label
			if (table.getSelectionModel().getSelectedItem() != null) {
				int arid = 0;
				int alid = 0;
				AllMusic m = table.getSelectionModel().getSelectedItem();
				String sn=m.getSong_name().toString();
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
				}
				song_download=m.getSong_name().toString()+"_"+m.getArtist().toString()+"_"+m.getAlbum().toString();
				//duration_playlist=m.getDuration().toString();


				System.out.println(sn);
	
				File file=new File("//Anon-Laptop/Users/Public/"+m.getSong_name().toString()+"_"+m.getArtist().toString()+"_"+m.getAlbum().toString()+ ".mp3");
				//File file = new File("D:/server/" + m.getSong_name().toString()+"_"+m.getArtist().toString()+"_"+m.getAlbum().toString()+ ".mp3");
				source = file.toURI().toString();
				System.out.println(source);
			}
		});
	}

}
