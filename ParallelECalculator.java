import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ParallelECalculator {

    private int tasks;
    private int precision;
    private boolean isQuiet;

    private long totalTime = 0;
    private BigDecimal result = null;

    public ParallelECalculator(int tasks, int precision, boolean isQuiet) {
        this.tasks = tasks;
        this.precision = precision;
        this.isQuiet = isQuiet;
        this.result = new BigDecimal(0.0);
    }

    public void calculate() throws InterruptedException {


        ExecutorService pool = Executors.newFixedThreadPool(tasks);
        ArrayList<CalculationCaller> sums = new ArrayList<CalculationCaller>();

        for(int i=0;i<tasks;++i) {
            sums.add(new CalculationCaller(i, precision, tasks, isQuiet));
        }


        long startedTime = System.currentTimeMillis();
        try {
            for(Future<BigDecimal> f : pool.invokeAll(sums)) {
                result = result.add(f.get());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        long totalTime = System.currentTimeMillis() - startedTime;
        pool.shutdown();

        System.out.println("Total time is: " + totalTime + " ms");

    }

    public BigDecimal getResult() {
        return result;
    }
}
