package Exercice2;

public class VectorStackInteger {

	public static void main(String[] args) {
		Vector vector = new Vector();
		Stack stack = new Stack();

		for (int i = 0; i < 10; ++i) {
			Integer integer = i;
			vector.add(integer);
			stack.push(integer);
		}

		System.out.println("vector :");
		System.out.println(vector.toString());

		System.out.println("stack :");
		System.out.println(stack.toString());
	}

}
