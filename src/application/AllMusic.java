package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;

public class AllMusic extends VBox {
	public int song_id;
	public int getSong_id() {
		return song_id;
	}
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	public SimpleStringProperty song_name;
	public SimpleStringProperty artist;
	public SimpleStringProperty album;
	public SimpleStringProperty duration;
	public SimpleStringProperty hit;

	public AllMusic(int id,String song_name, String artist, String album,String duration,String hit) {
		super();
		this.song_name = new SimpleStringProperty(song_name);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.duration = new SimpleStringProperty(duration);
		this.hit = new SimpleStringProperty(hit);
		this.song_id=id;

	}
	public String getDuration() {
		return duration.get();
	}
	public void setDuration(SimpleStringProperty duration) {
		this.duration = duration;
	}
	public String getSong_name() {
		return song_name.get();
	}
	public void setSong_name(SimpleStringProperty song_name) {
		this.song_name = song_name;
	}
	public String getArtist() {
		return artist.get();
	}
	public void setArtist(SimpleStringProperty artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album.get();
	}
	public void setAlbum(SimpleStringProperty album) {
		this.album = album;
	}
	public String getHit(){
		return hit.get();
	}
	public void setHit(SimpleStringProperty hit){
		this.hit=hit;
	}
	

}
