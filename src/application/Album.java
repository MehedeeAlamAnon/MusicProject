package application;

public class Album {

	public String album_name;
	int album_id;

	public Album(int id,String album_name) {
		super();
		this.album_id=id;
		this.album_name = album_name;
	}
	
	public int getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(int  id) {
		this.album_id = id;
	}

	public String getAlbum_name() {
		return album_name;
	}

	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}

}
