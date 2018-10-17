package Exercice3;

public class LaTeXListFormat implements ListFormat {

	@Override
	public String begin() {
		return "\\begin{itemize}";
	}

	@Override
	public String end() {
		return "\\end{itemize}";
	}

	@Override
	public String beginItem() {
		return "\\item ";
	}

	@Override
	public String endItem() {
		return "";
	}

}
