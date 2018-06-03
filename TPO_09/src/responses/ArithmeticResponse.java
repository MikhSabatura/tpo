package responses;

import java.math.BigDecimal;

public class ArithmeticResponse implements IResponse {

    private BigDecimal result;

    public ArithmeticResponse(BigDecimal result) {
        this.result = result;
    }

    @Override
    public BigDecimal getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "result = " + result + " type = arithmetic";
    }
}
