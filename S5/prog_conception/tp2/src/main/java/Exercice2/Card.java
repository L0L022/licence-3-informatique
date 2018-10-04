
package Exercice2;

/**
 * Une carte avec une enseigne et une valeur.
 *
 * @author Loïc Escales
 *
 */
public class Card {
	public enum Suit {
		Clubs, Diamonds, Hearts, Spades
	}

	public enum Rank {
		Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
	}

	public Suit suit;
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
}
