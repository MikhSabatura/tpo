package exceptions;

public class Assignment02Exception extends RuntimeException {

    public Assignment02Exception() {
        super();
    }

    public Assignment02Exception(Exception cause) {
        super(cause);
    }

    public Assignment02Exception(String message) {
        super(message);
    }

    public Assignment02Exception(String message, Exception cause) {
        super(message, cause);
    }

}
