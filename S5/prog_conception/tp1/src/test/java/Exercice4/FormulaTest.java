package Exercice4;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;


public class FormulaTest {
	
	@Test
	public void variable() {
		Formula f = new Variable("x", 5.0);
		assertThat(f.asValue(), equalTo(5.0));
		assertThat(f.asString(), equalTo("x"));
	}
	
	@Test
	public void sum() {
		Formula[] variables = { new Variable("x", 1.0), new Variable("y", 2.0), new Variable("z", 3.0) };
		Formula f = new Sum(variables);
		assertThat(f.asValue(), equalTo(6.0));
		assertThat(f.asString(), equalTo("(x+y+z)"));
	}
	
	@Test
	public void product() {
		Formula[] variables = { new Variable("x", 1.0), new Variable("y", 2.0), new Variable("z", 3.0) };
		Formula f = new Product(variables);
		assertThat(f.asValue(), equalTo(6.0));
		assertThat(f.asString(), equalTo("(x*y*z)"));
	}
	
	@Test
	public void absoluteValue_positive() {
		Formula f = new AbsoluteValue(new Variable("x", 5.0));
		assertThat(f.asValue(), equalTo(5.0));
		assertThat(f.asString(), equalTo("|x|"));
	}
	
	@Test
	public void absoluteValue_negative() {
		Formula f = new AbsoluteValue(new Variable("x", -5.0));
		assertThat(f.asValue(), equalTo(5.0));
		assertThat(f.asString(), equalTo("|x|"));
	}
	
	@Test
	public void square() {
		Formula f = new Square(new Variable("x", 5.0));
		assertThat(f.asValue(), equalTo(25.0));
		assertThat(f.asString(), equalTo("(x)²"));
	}
	
	@Test
	public void squareRoot() {
		Formula f = new SquareRoot(new Variable("x", 25.0));
		assertThat(f.asValue(), equalTo(5.0));
		assertThat(f.asString(), equalTo("√(x)"));
	}
	
	@Test
	public void power() {
		Formula f = new Power(new Variable("x", 5.0), 2.0);
		assertThat(f.asValue(), equalTo(25.0));
		assertThat(f.asString(), equalTo("(x)^2.0"));
	}
	
	@Test
	public void minimum() {
		Formula[] variables = { new Variable("x", 1.0), new Variable("y", 2.0), new Variable("z", 3.0) };
		Formula f = new Minimum(variables);
		assertThat(f.asValue(), equalTo(1.0));
		assertThat(f.asString(), equalTo("min(x, y, z)"));
	}
	
	@Test
	public void maximum() {
		Formula[] variables = { new Variable("x", 1.0), new Variable("y", 2.0), new Variable("z", 3.0) };
		Formula f = new Maximum(variables);
		assertThat(f.asValue(), equalTo(3.0));
		assertThat(f.asString(), equalTo("max(x, y, z)"));
	}

}
