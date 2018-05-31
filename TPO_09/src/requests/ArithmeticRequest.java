package requests;

import java.math.BigDecimal;

public class ArithmeticRequest implements IRequest {

    private final BigDecimal param1;
    private final BigDecimal param2;
    private final ArithmeticRequestType type;

    public ArithmeticRequest(BigDecimal param1, BigDecimal param2, ArithmeticRequestType type) {
        this.param1 = param1;
        this.param2 = param2;
        this.type = type;
    }

    public BigDecimal getParam1() {
        return param1;
    }

    public BigDecimal getParam2() {
        return param2;
    }

    public ArithmeticRequestType getType() {
        return type;
    }

}
