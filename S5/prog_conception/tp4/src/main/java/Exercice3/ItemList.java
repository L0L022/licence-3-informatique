package Exercice3;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
	private List<String> items;

	public ItemList() {
		items = new ArrayList<>();
	}

	public void add(String item) {
		items.add(item);
	}

	public void print(ListFormat listFormat) {
		System.out.print(listFormat.begin());
		for (String item : items) {
			System.out.print(listFormat.beginItem() + item + listFormat.endItem());
		}
		System.out.print(listFormat.end());
	}
}
