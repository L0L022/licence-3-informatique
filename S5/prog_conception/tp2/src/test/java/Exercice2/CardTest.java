package Exercice2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class CardTest {

	@Test
	public void testToString() {
		Card[] cards = { new Card(Card.Suit.Clubs, Card.Rank.Ace), new Card(Card.Suit.Diamonds, Card.Rank.Two),
				new Card(Card.Suit.Hearts, Card.Rank.Three), new Card(Card.Suit.Spades, Card.Rank.Four),
				new Card(Card.Suit.Spades, Card.Rank.Five), new Card(Card.Suit.Spades, Card.Rank.Six),
				new Card(Card.Suit.Spades, Card.Rank.Seven), new Card(Card.Suit.Spades, Card.Rank.Eight),
				new Card(Card.Suit.Spades, Card.Rank.Nine), new Card(Card.Suit.Spades, Card.Rank.Ten),
				new Card(Card.Suit.Spades, Card.Rank.Jack), new Card(Card.Suit.Spades, Card.Rank.Queen),
				new Card(Card.Suit.Spades, Card.Rank.King) };
		String[] answers = { "as de trèfles", "2 de carreaux", "3 de coeurs", "4 de piques", "5 de piques",
				"6 de piques", "7 de piques", "8 de piques", "9 de piques", "10 de piques", "valet de piques",
				"dame de piques", "roi de piques" };
		for (int i = 0; i < 13; ++i) {
			assertThat(cards[i].toString(), equalTo(answers[i]));
		}
	}

}
