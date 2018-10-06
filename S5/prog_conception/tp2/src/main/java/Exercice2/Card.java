
package Exercice2;

/**
 * Une carte avec une enseigne et une valeur.
 *
 * @author Loïc Escales
 *
 */
public class Card {

	/**
	 * L'enseigne d'une carte.
	 *
	 * @author Loïc Escales
	 *
	 */
	public enum Suit {
		Clubs, Diamonds, Hearts, Spades
	}

	/**
	 * Renvoie la chaine de caractères associée à l'enseigne donnée.
	 *
	 * @param suit Une enseigne.
	 * @return La chaine de caractères correspondant à l'enseigne.
	 */
	private static String suitToString(Suit suit) {
		switch (suit) {
		case Clubs:
			return "trèfles";
		case Diamonds:
			return "carreaux";
		case Hearts:
			return "coeurs";
		case Spades:
			return "piques";
		default:
			return null;
		}
	}

	/**
	 * La valeur d'une carte.
	 *
	 * @author Loïc Escales
	 *
	 */
	public enum Rank {
		Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
	}

	/**
	 * Renvoie la chaine de caractères associée à la valeur donnée.
	 *
	 * @param rank Une valeur.
	 * @return La chaine de caractères correspondant à la valeur.
	 */
	private static String rankToString(Rank rank) {
		switch (rank) {
		case Ace:
			return "as";
		case Two:
			return "2";
		case Three:
			return "3";
		case Four:
			return "4";
		case Five:
			return "5";
		case Six:
			return "6";
		case Seven:
			return "7";
		case Eight:
			return "8";
		case Nine:
			return "9";
		case Ten:
			return "10";
		case Jack:
			return "valet";
		case Queen:
			return "dame";
		case King:
			return "roi";
		default:
			return null;
		}
	}

	/**
	 * L'enseigne de la carte.
	 */
	public Suit suit;

	/**
	 * La valeur de la carte.
	 */
	public Rank rank;

	/**
	 * Construit une carte.
	 *
	 * @param suit L'enseigne de la carte.
	 * @param rank La valeur de la carte.
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public String toString() {
		return rankToString(rank) + " de " + suitToString(suit);
	}
}
