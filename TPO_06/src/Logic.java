import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Logic {

    public static BigDecimal processParameters(String param1, String param2) {
        return new BigDecimal(param1).add(new BigDecimal(param2));
    }

}
