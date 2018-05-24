package communciates;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddResponse implements Serializable {

    private BigDecimal response;

    public AddResponse(BigDecimal response) {
        this.response = response;
    }

    public BigDecimal getResponse() {
        return response;
    }

    public void setResponse(BigDecimal response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "response = " + response;
    }
}
