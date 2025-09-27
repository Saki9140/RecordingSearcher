import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Searcher implements SearchOperations{
	
	//private Set<String> artists = new HashSet<>();
	//private Set<String> titles = new HashSet<>();
	private Set<String> genres = new HashSet<>();
	private Map<String, Recording> recsByTitle = new HashMap<>();
	private SortedMap<Integer, Set<Recording>> recsByYear = new TreeMap<>();
	private Map<String, Set<Recording>> recsByArtist = new HashMap<>();
	private Map<String, Set<Recording>> recsByGenre = new HashMap<>();
	
	public Searcher(Collection<Recording> data) {
		
		Collection<Recording> recordings = data;

		for(Recording recording : recordings){
			
			genres.addAll(recording.getGenre());
			recsByTitle.put(recording.getTitle(), recording);

			Set<Recording> set = recsByYear.get(recording.getYear());
			if(set == null){
				set = new HashSet<>();
				recsByYear.put(recording.getYear(), set);
			
			}
			set.add(recording);

			Set<Recording> sameArtist = recsByArtist.get(recording.getArtist());
			if(sameArtist == null){
				sameArtist = new HashSet<>();
				recsByArtist.put(recording.getArtist(), sameArtist);
			}
			sameArtist.add(recording);

			for(String genre : recording.getGenre()){
				Set<Recording> sameGenre = recsByGenre.get(genre);
				if(sameGenre == null){
					sameGenre = new HashSet<>();
					recsByGenre.put(genre, sameGenre);
				}
				sameGenre.add(recording);
			}

		}
	}

	@Override
	public long numberOfArtists() {
		return recsByArtist.size();
	}

	@Override
	public long numberOfGenres() {
		return recsByGenre.size();
	}

	@Override
	public long numberOfTitles() {
		return recsByTitle.size();
	}

	@Override
	public boolean doesArtistExist(String name) {
		return recsByArtist.containsKey(name);
	}

	@Override
	public Collection<String> getGenres() {
		return Collections.unmodifiableCollection(recsByGenre.keySet());
	}

	@Override
	public Recording getRecordingByName(String title) {
		return recsByTitle.get(title);

	}

	@Override
	public Collection<Recording> getRecordingsAfter(int year) {
		Set<Recording> set = new HashSet<>();
		for(Set<Recording> recs : recsByYear.tailMap(year).values()){
			set.addAll(recs);
		}
		return Collections.unmodifiableCollection(set);
	}

	@Override
	public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
		SortedSet<Recording> set = new TreeSet<>(Recording.getYearCmp());
		set.addAll(recsByArtist.get(artist));
		return Collections.unmodifiableSortedSet(set);

	}

	@Override
	public Collection<Recording> getRecordingsByGenre(String genre) {
		return Collections.unmodifiableCollection(recsByGenre.getOrDefault(genre, Collections.EMPTY_SET));
	}

	@Override
	public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
		Set<Recording> set = new HashSet<>();
		Set<Recording> sameGenre = recsByGenre.get(genre);
		for(Set<Recording> recs : recsByYear.subMap(yearFrom, yearTo + 1).values()){
			recs.retainAll(sameGenre);
			set.addAll(recs);
		}
		return Collections.unmodifiableCollection(set);
	}

	@Override
	public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
		Set<Recording> set = new HashSet<>(offered);
		set.removeAll(recsByTitle.values());
		return Collections.unmodifiableCollection(set);
	}

	
}

