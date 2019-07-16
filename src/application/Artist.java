package application;

public class Artist {
	public String artist_name;
	int artist_id;
	
	public int getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(int  id) {
		this.artist_id = id;
	}

	public String getArtist_name() {
		return artist_name;
	}

	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}

	public Artist(int id,String artist_name) {
		super();
		this.artist_id=id;
		this.artist_name = artist_name;
	}
	

}
