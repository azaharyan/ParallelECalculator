import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;

public class CalculationCaller implements Callable<BigDecimal> {

    private int start;
    private int step;
    private int precision;
    private boolean isQuiet;
    private BigDecimal result = null;

    public CalculationCaller(int start, int precision, int step, boolean isQuiet) {
        this.start =start;
        this.step = step;
        this.precision = precision;
        this.isQuiet = isQuiet;
        result = new BigDecimal(0.0);
    }

    @Override
    public BigDecimal call() {
        if(!this.isQuiet)
            System.out.println("Thread " + Thread.currentThread().getName() + " started");

        long startedTime = System.currentTimeMillis();

        try {
            for(int k=start; k <precision; k+=step) {
                BigDecimal first = BigDecimal.valueOf(3 - 4*k*k).setScale(precision);
                BigDecimal second = calculateFact(2*k +1);
                BigDecimal current = first.divide(second, RoundingMode.FLOOR);
                result = result.add(current).setScale(precision);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.currentTimeMillis();

        return  result;
    }

    private BigDecimal calculateFact(Integer k) {
        if(k.equals(0) || k.equals(1))
            return BigDecimal.valueOf(1);

        BigDecimal result = BigDecimal.valueOf(k);

        for(int i=k-1; i>=2;i--)
            result = result.multiply(BigDecimal.valueOf(i));

        return result;
    }
}
