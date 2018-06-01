package exceptions;

import javax.naming.NamingException;

public class Assignment_09_exception extends RuntimeException {

    public Assignment_09_exception(String message) {
        super(message);
    }

    public Assignment_09_exception(Throwable cause) {
        super(cause);
    }

    public Assignment_09_exception(String message, Throwable cause) {
        super(message, cause);
    }

}
