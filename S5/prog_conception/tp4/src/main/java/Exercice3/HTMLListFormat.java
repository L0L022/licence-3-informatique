package Exercice3;

public class HTMLListFormat implements ListFormat {

	@Override
	public String begin() {
		return "<ul>";
	}

	@Override
	public String end() {
		return "</ul>";
	}

	@Override
	public String beginItem() {
		return "<li>";
	}

	@Override
	public String endItem() {
		return "</li>";
	}

}
