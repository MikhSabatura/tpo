package responses;

import java.math.BigDecimal;
import java.util.Random;

public class RandomResponse implements IResponse {

    private BigDecimal result;

    public RandomResponse() {
        this.result = new BigDecimal(new Random().nextInt());
    }

    @Override
    public BigDecimal getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "result = " + result + " type = random";
    }
}
