package requests;

import java.io.Serializable;

public interface IRequest extends Serializable {
    String getSenderName();
}
