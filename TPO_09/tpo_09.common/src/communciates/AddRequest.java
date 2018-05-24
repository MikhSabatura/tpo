package communciates;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddRequest implements Serializable {

    private BigDecimal param1;
    private BigDecimal param2;

    public AddRequest(BigDecimal param1, BigDecimal param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public BigDecimal getParam1() {
        return param1;
    }

    public void setParam1(BigDecimal param1) {
        this.param1 = param1;
    }

    public BigDecimal getParam2() {
        return param2;
    }

    public void setParam2(BigDecimal param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "p1 = " + param1 + " p2 = " + param2;
    }
}
