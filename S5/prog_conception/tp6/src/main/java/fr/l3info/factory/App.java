package fr.l3info.factory;
import fr.l3info.factory.strategy.StrategyFormulaFactory;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Calculator c = new Calculator(new StrategyFormulaFactory());
        Formula f = c.parseAllArgs(args);
        System.out.println(f.asValue());
    }
}
