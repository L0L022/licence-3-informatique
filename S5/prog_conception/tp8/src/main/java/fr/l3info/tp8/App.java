package fr.l3info.tp8;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Variable a = new Variable("a", 2);
		Variable b = new Variable("b", 1);
		Variable c = new Variable("c", 2);
		Variable d = new Variable("d", 1);

		Formula formula = new SquareRoot(new Sum(new Product(a, b), new Product(c, d)));

		System.out.println(formula.accept(new PlainTextVisitor()));
		System.out.println(formula.accept(new XMLTextVisitor()));
		System.out.println(formula.accept(new LaTeXVisitor()));
		System.out.println(formula.accept(new EvaluatorVisitor()));
	}
}
