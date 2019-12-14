import net.sourceforge.jFuzzyLogic.FIS;

public class tipper {
    public tipper() {

    }

    public void sim() throws Exception {
        // Load from 'FCL' file
//        String fileName2 = "C:/Users/job_p/Data/HvA/Vakken/Intelligent Systems/fuzzy logic/fuzzy-logic/libs/jFuzzyLogic.jar!/tipper.fcl";
        String fileName = "src/fcl/tipper.fcl";
        FIS fis = FIS.load(fileName);
//        FIS fis = FIS.load(fileName2,true);
        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '"
                    + fileName + "'");
            return;
        }

        // Show
//        JFuzzyChart.get().chart(fis);
//        fis.chart();

        // Set inputs
        fis.setVariable("service", 3);
        fis.setVariable("food", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's
//        net.sourceforge.jFuzzyLogic.defuzzifier.
//        fis.getVariable("tip").getDefuzzifier().defuzzify();
//                .chartDefuzzifier(true);

        // Print ruleSet
        System.out.println(fis);
    }

    public void run() {
        try {
            sim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new tipper().run();
    }
}
