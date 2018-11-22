package fr.l3info.tp8;

/**
 * Le produit de plusieurs formules.
 *
 * @author Loïc Escales
 *
 */
public class Product extends VariadicOperator {

	/**
	 * Construit Product.
	 *
	 * @param formulas La liste des formules à multiplier.
	 */
	public Product(Formula... formulas) {
		super(formulas);
	}

	@Override
	protected String symbol() {
		return "*";
	}

	@Override
	protected double initialValue() {
		return 1;
	}

	@Override
	protected double cumulativeValue(double accumulator, double value) {
		return accumulator * value;
	}

	@Override
	public <R> R accept(FormulaVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
