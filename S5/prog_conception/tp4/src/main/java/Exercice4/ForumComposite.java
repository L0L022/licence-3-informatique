package Exercice4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForumComposite implements ForumElement {
	private List<ForumElement> elements;

	public ForumComposite() {
		elements = new ArrayList<>();
	}

	public void addElement(ForumElement e) {
		elements.add(e);
	}

	public List<ForumElement> getElements() {
		return elements;
	}

	@Override
	public void print(int nbSpaces) {
		for (ForumElement e : elements) {
			e.print(nbSpaces + 4);
		}
	}

	@Override
	public Iterator<String> iterator() {
		return new ForumCompositeIterator();
	}

	private class ForumCompositeIterator implements Iterator<String> {
		private int currentElement;
		private Iterator<String> currentIterator;

		public ForumCompositeIterator() {
			currentElement = -1;
			currentIterator = null;
		}

		@Override
		public boolean hasNext() {
			if (currentIterator != null && currentIterator.hasNext()) {
				return true;
			}

			if (currentElement + 1 < elements.size()) {
				++currentElement;
				Iterator<String> lastIterator = currentIterator;

				currentIterator = elements.get(currentElement).iterator();
				boolean r = hasNext();

				--currentElement;
				currentIterator = lastIterator;

				return r;
			}

			return false;
		}

		@Override
		public String next() {
			if (currentIterator != null && currentIterator.hasNext()) {
				return currentIterator.next();
			}

			if (currentElement + 1 < elements.size()) {
				++currentElement;
				currentIterator = elements.get(currentElement).iterator();

				return next();
			}

			return null;
		}

	}
}
