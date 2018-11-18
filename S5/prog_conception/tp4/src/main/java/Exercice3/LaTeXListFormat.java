package Exercice3;

public class LaTeXListFormat implements ListFormat {

	@Override
	public String begin() {
		return "\\begin{itemize}\n";
	}

	@Override
	public String end() {
		return "\\end{itemize}\n";
	}

	@Override
	public String beginItem() {
		return "\\item ";
	}

	@Override
	public String endItem() {
		return "\n";
	}

}
