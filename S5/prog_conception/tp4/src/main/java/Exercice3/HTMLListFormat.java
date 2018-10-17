package Exercice3;

public class HTMLListFormat implements ListFormat {

	@Override
	public String begin() {
		return "<li>";
	}

	@Override
	public String end() {
		return "</li>";
	}

	@Override
	public String beginItem() {
		return "<ul>";
	}

	@Override
	public String endItem() {
		return "</ul>";
	}

}
