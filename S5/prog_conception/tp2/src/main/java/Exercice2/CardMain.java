package Exercice2;

import java.util.Random;

public class CardMain {

	static Random random;

	public static void main(String[] args) {
		random = new Random();

		Vector vector = new Vector();
		Stack stack = new Stack();

		for (int i = 0; i < 10; ++i) {
			Card.Rank rank = randomEnum(Card.Rank.class);
			Card.Suit suit = randomEnum(Card.Suit.class);
			vector.add(new Card(suit, rank));
			stack.push(new Card(suit, rank));
		}

		System.out.println("vector :");
		System.out.println(vector.toString());

		System.out.println("stack :");
		System.out.println(stack.toString());
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
