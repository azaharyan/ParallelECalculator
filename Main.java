public class Main {

    private static String outFile = "output.txt";
    private static boolean isQuiet = false;
    private static int precision = 8000;
    private static int tasks = 1;


    public static void main(String[] args) throws InterruptedException {
        try {
            parseArgs(args);
        } catch (Exception e) {
            System.err.println(String.format("CLI options error: %s", e.getMessage()));
            return;
        }
        calculateE();
    }

    private static void calculateE() throws InterruptedException {
        ParallelECalculator cal = new ParallelECalculator(tasks, precision, isQuiet);

        cal.calculate();

        System.out.println(cal.getResult());
    }

    private static void parseArgs(String[] args) throws IllegalArgumentException {
        for (int i = 0; i < args.length; ++i) {
            String currentArg = args[i];
            switch (currentArg) {
                case "-q":
                    isQuiet = true;
                    break;
                case "-t":
                    try {
                        tasks = Integer.parseInt(args[++i]);
                        if (tasks < 1) {
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    break;
                case "-p":
                    try {
                        precision = Integer.parseInt(args[++i]);
                        if (precision < 1) {

                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    break;
                case "-o":
                    try {
                        outFile = args[++i];
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    break;
            }
        }
    }
}
