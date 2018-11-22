package fr.l3info.tp8;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlainTextVisitorTest {

    private FormulaVisitor<String> visitor = new PlainTextVisitor();

    @Test
    public void testVariable() {
        Formula formula = new Variable("x", 12);
        String result = formula.accept(visitor);
        assertThat(result, is(equalTo("x")));
    }

    @Test
    public void testProduct() {
        Formula x = new Variable("x", 12);
        Formula y = new Variable("y", 12);
        Formula product = new Product(x,y);
        String result = product.accept(visitor);
        assertThat(result, is(equalTo("(x*y)")));
    }

    @Test
    public void testSum() {
        Formula x = new Variable("x", 12);
        Formula y = new Variable("y", 12);
        Formula sum = new Sum(x,y);
        String result = sum.accept(visitor);
        assertThat(result, is(equalTo("(x+y)")));
    }

}
