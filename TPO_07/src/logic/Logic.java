package logic;

import java.math.BigDecimal;

public class Logic {

    public static BigDecimal processParameters(String param1, String param2) {
        return new BigDecimal(param1).add(new BigDecimal(param2));
    }

}
