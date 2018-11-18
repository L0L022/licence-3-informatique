package Exercice4;

public class Forum extends ForumComposite {
	public void print() {
		print(0);
	}

	@Override
	public void print(int nbSpaces) {
		for (int i = 0; i < nbSpaces; ++i) {
			System.out.print(" ");
		}
		System.out.println("Forum :");

		super.print(nbSpaces);
	}
}
