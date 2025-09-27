import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class Recording implements Comparable<Recording>{
	private final int year;
	private final String artist;
	private final String title;
	private final String type;
	private final Set<String> genre;

	public Recording(String title, String artist, int year, String type, Set<String> genre) {
		this.title = title;
		this.year = year;
		this.artist = artist;
		this.type = type;
		this.genre = genre;
	}

	public String getArtist() {
		return artist;
	}

	public Collection<String> getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
	}
 
	@Override
	public boolean equals(Object otherObject){
		if(this == otherObject){
			return true;
		}
		if(otherObject instanceof Recording){
			Recording r = (Recording) otherObject;
			return title.equals(r.title) && artist.equals(r.artist) && year == r.year; 
		}else{
			return false;
		}
	} 

	@Override
	public int hashCode(){
		return Objects.hash(title, artist, year);
	}

	private static class ArtistCmp implements Comparator<Recording>{
		public int compare(Recording r1, Recording r2){
			return r1.getArtist().compareTo(r2.getArtist()); // Jämför artistnamnen för r1 och r2
		}
	}

	public static Comparator<Recording> getArtistCmp(){
		return new ArtistCmp(); // Returnerar en instans av ArtistCmp som en Comparator för att jämföra Recording-objekt baserat på artistnamn
	}

	public static Comparator<Recording> getTitleCmp(){
		return Comparator.comparing(Recording::getTitle); // Returnerar en Comparator som jämför Recording-objekt baserat på titel
	}

	public static Comparator<Recording> getYearCmp(){
		return Comparator.comparing(Recording::getYear); // Returnerar en Comparator som jämför Recording-objekt baserat på år
	}
		



	@Override
	public int compareTo(Recording r){
		if(this.artist.compareTo(r.artist) < 0){
			return -1;
		}else if(this.artist.compareTo(r.artist) > 0){
			return 1;
		}else if(this.title.compareTo(r.title) < 0){
			return -1;
		}else if(this.title.compareTo(r.title) > 0){
			return 1;
		}else{
			return this.year - r.year;
		} 
	}
	
}