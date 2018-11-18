package Exercice4;

public class Topic extends ForumComposite {

	private String title;

	public Topic(String title) {
		this.title = title;
	}

	@Override
	public void print(int nbSpaces) {
		for (int i = 0; i < nbSpaces; ++i) {
			System.out.print(" ");
		}
		System.out.println("Topic : " + title);

		super.print(nbSpaces);
	}

}
