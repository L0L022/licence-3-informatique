package Exercice4;

import java.util.Iterator;

public class Message implements ForumElement {
	private String message;

	public Message(String message) {
		this.message = message;
	}

	@Override
	public void print(int nbSpaces) {
		for (int i = 0; i < nbSpaces; ++i) {
			System.out.print(" ");
		}
		System.out.println(message);
	}

	@Override
	public Iterator<String> iterator() {
		return new MessageIterator();
	}

	private class MessageIterator implements Iterator<String> {
		private boolean used;

		public MessageIterator() {
			used = false;
		}

		@Override
		public boolean hasNext() {
			return !used;
		}

		@Override
		public String next() {
			if (used) {
				return null;
			}

			used = true;
			return message;
		}

	}
}
