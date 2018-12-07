package fr.l3info.factory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


import fr.l3info.factory.templatemethod.TemplateMethodFormulaFactory;
import fr.l3info.factory.strategy.StrategyFormulaFactory;

@RunWith(Parameterized.class)
public class FormulaFactoryTest {

    @Parameters
    public static Object[] data() {
        return new Object[] {
                new TemplateMethodFormulaFactory(),
                new StrategyFormulaFactory()
        };
    }

    @Parameter
    public AbstractFormulaFactory factory;

    @Test
    public void createSum() {
        Formula formula = factory.createSum(new Constant(2), new Constant(3));
        assertThat(formula.asValue(), is(equalTo(5.0)));
        assertThat(formula.asString(), is(equalTo("(2.0+3.0)")));
    }

}