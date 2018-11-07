package dictionary;

public class WordDistance implements Comparable<WordDistance> {
	public String word;
	public int distance;

	public WordDistance(String word, int distance) {
		this.word = word;
		this.distance = distance;
	}

	@Override
	public int compareTo(WordDistance other) {
		return Integer.compare(distance, other.distance);
	}
}
