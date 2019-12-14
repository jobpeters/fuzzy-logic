import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.imex.FldExporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Cosine;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class SimpleDimmer {

    public SimpleDimmer() {

    }

    //Method 1: Set up the engine manually.
    public void method1() {
        Engine engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("Ambient");
        inputVariable.setRange(0.000, 1.000);
        inputVariable.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
        inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        inputVariable.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
        engine.addInputVariable(inputVariable);

        OutputVariable outputVariable = new OutputVariable();
        outputVariable.setEnabled(true);
        outputVariable.setName("Power");
        outputVariable.setRange(0.000, 1.000);
        outputVariable.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable.setDefuzzifier(new Centroid(200));
        outputVariable.setDefaultValue(Double.NaN);
        outputVariable.setLockPreviousOutputValue(false);
        outputVariable.setLockOutputValueInRange(false);
        outputVariable.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        outputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        outputVariable.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine.addOutputVariable(outputVariable);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine));
    }
//    //Method 2: Load from a file
//    public void method2() {
//        Engine engine = null;
//        String configurationFile = "/SimpleDimmer.fll";
//        URL url = SimpleDimmer.class.getResource(configurationFile);
//        try {
//            engine = new FllImporter().fromFile(new File(url.toURI()));
//        } catch (Exception ex) {
//            FuzzyLite.logger().log(Level.SEVERE, ex.toString(), ex);
//        }
//
//        FuzzyLite.logger().info(new FldExporter().toString(engine));
//    }

    public void anotherTest(){
        Engine engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("Ambient");
        inputVariable.setRange(0.000, 1.000);
        inputVariable.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
        inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        inputVariable.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
        engine.addInputVariable(inputVariable);

        OutputVariable outputVariable1 = new OutputVariable();
        outputVariable1.setEnabled(true);
        outputVariable1.setName("Power");
        outputVariable1.setRange(0.000, 1.000);
        outputVariable1.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable1.setDefuzzifier(new Centroid(200));
        outputVariable1.setDefaultValue(Double.NaN);
        outputVariable1.setLockPreviousOutputValue(false);
        outputVariable1.setLockOutputValueInRange(false);
        outputVariable1.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        outputVariable1.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        outputVariable1.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine.addOutputVariable(outputVariable1);

        OutputVariable outputVariable2 = new OutputVariable();
        outputVariable2.setEnabled(true);
        outputVariable2.setName("InversePower");
        outputVariable2.setRange(0.000, 1.000);
        outputVariable2.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable2.setDefuzzifier(new Centroid(500));
        outputVariable2.setDefaultValue(Double.NaN);
        outputVariable2.setLockPreviousOutputValue(false);
        outputVariable2.setLockOutputValueInRange(false);
        outputVariable2.addTerm(new Cosine("LOW", 0.200, 0.500));
        outputVariable2.addTerm(new Cosine("MEDIUM", 0.500, 0.500));
        outputVariable2.addTerm(new Cosine("HIGH", 0.800, 0.500));
        engine.addOutputVariable(outputVariable2);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        ruleBlock.addRule(Rule.parse("if Power is LOW then InversePower is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Power is MEDIUM then InversePower is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Power is HIGH then InversePower is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine));
    }

    public void run() {
        method1();
        anotherTest();
    }

    public static void main(String[] args) {
        new SimpleDimmer().run();
    }

}