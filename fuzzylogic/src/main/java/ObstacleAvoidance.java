//File: ObstacleAvoidance.java

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.imex.FldExporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.DrasticProduct;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import java.io.File;

public class ObstacleAvoidance {
    //Method 1: Set up the engine manually.
    public static Engine createEngine() {
        Engine engine = new Engine();
        engine.setName("ObstacleAvoidance");
//        InputVariable: obstacle
//        enabled: true
//        range: 0.000 1.000
//        lock-range: false
//        term: left Ramp 1.000 0.000
//        term: right Ramp 0.000 1.000
        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("obstacle");
        inputVariable.setRange(0.000, 1.000);
        inputVariable.addTerm(new Ramp("left", 1.000, 0.000));
        inputVariable.addTerm(new Ramp("right", 0.000, 1.000));
        engine.addInputVariable(inputVariable);
//        OutputVariable: mSteer
//        enabled: true
//        range: 0.000 1.000
//        lock-range: false
//        aggregation: Maximum
//        defuzzifier: Centroid 100
//        default: nan
//        lock-previous: false
//        term: left Ramp 1.000 0.000
//        term: right Ramp 0.000 1.000
        OutputVariable outputVariable = new OutputVariable();
        outputVariable.setEnabled(true);
        outputVariable.setName("mSteer");
        outputVariable.setRange(0.000, 1.000);
        outputVariable.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable.setDefuzzifier(new Centroid(100));
        outputVariable.setDefaultValue(Double.NaN);
        outputVariable.setLockPreviousOutputValue(false);
        outputVariable.setLockOutputValueInRange(false);
        outputVariable.addTerm(new Ramp("left", 1.000, 0.000));
        outputVariable.addTerm(new Ramp("right", 0.000, 1.000));
        engine.addOutputVariable(outputVariable);
//        RuleBlock: mamdani
//        enabled: true
//        conjunction: none
//        disjunction: none
//        implication: AlgebraicProduct
//        activation: General
//        rule: if obstacle is left then mSteer is right
//        rule: if obstacle is right then mSteer is left
        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("mamdani");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new DrasticProduct());
        ruleBlock.addRule(Rule.parse("if obstacle is left then mSteer is right", engine));
        ruleBlock.addRule(Rule.parse("if obstacle is right then mSteer is left", engine));
        engine.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine));
        return engine;
    }
    public static void main(String[] args){
        Engine engine = new Engine();
        try {
            File fllFile = new File("C:/Users/job_p/Data/HvA/Vakken/Intelligent Systems/fuzzy logic/fuzzy-logic/jfuzzylogicmvn/fll/ObstacleAvoidance.fll");
            System.out.println(fllFile.toString());
            engine = createEngine();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        StringBuilder status = new StringBuilder();
        if (! engine.isReady(status))
            throw new RuntimeException("[engine error] engine is not ready:\n" + status);

        InputVariable obstacle = engine.getInputVariable("obstacle");
        OutputVariable steer = engine.getOutputVariable("mSteer");
//        int i = 0;
//        double location = 0.500;
//        obstacle.setInputValue(location);
//        engine.process();
//        FuzzyLite.logger().info(String.format("\nsteer.term.left = %s \n steer.term.right = %s ",
//                steer.getTerm("left").getHeight(),
//                steer.getTerm("right").getHeight()));
//        FuzzyLite.logger().info(String.format(
//                "obstacle.input = %s -> steer.output = %s",
//                Op.str(location), Op.str(steer.getOutputValue())));
        for (int i = 0; i <= 50; ++i){
            double location = obstacle.getMinimum() + i * (obstacle.range() / 50);
            obstacle.setInputValue(location);
            engine.process();

            FuzzyLite.logger().info(String.format(
                    "obstacle.input = %s -> steer.output = %s",
                    Op.str(location), Op.str(steer.getOutputValue())));
            FuzzyLite.logger().info(String.format(
                    "\nobstacle fuzzy input value (%s). \nsteer fuzzy output value(%s). \nsteer fuzzy output(%s). ",
                    obstacle.fuzzyInputValue(),
                    steer.fuzzyOutputValue(),
                    steer.fuzzyOutput().toString()
            ));
        }
    }
}