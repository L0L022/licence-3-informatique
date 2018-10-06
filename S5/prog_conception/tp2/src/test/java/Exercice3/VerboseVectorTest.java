package Exercice3;

import org.junit.Test;

import Exercice2.Card;
import Exercice2.Vector;

public class VerboseVectorTest {

	@Test
	public void testVerboseVector() {
		Vector vector = new VerboseVector();
		vector.add(new Card(Card.Suit.Clubs, Card.Rank.Two));
		vector.add(Integer.valueOf(2));
		vector.set(1, Integer.valueOf(3));
		vector.get(1);
	}

}
