package messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static messages.MessageType.*;

public abstract class Message {

    protected static final String TYPE_GROUP = "TYPE";
    protected static final String TYPE_PATTERN = "((" + ADD + ")|(" + ECHO + ")|(" + ILLEGAL + "))";
    protected static final String TYPE_PATTERN_NAMED = "(?<" + TYPE_GROUP + ">" + TYPE_PATTERN + ")";

    protected static final String ELEMENT_PATTERN = "\\S+";
    protected static final String NUMERIC_PATTERN = "^(0|[1-9]\\d*)(\\.\\d+)?";

    private MessageType type;

    protected Message(MessageType type) {
        this.type = type;
    }

    protected static boolean isNumber(String str) {
        return Pattern.compile(NUMERIC_PATTERN)
                .matcher(str)
                .matches();
    }

    //returns null in case the operation type is invalid
    protected static MessageType parseMessageType(String reqStr) {
        String typeExtractPattern = TYPE_PATTERN_NAMED + ".*";
        Pattern pattern = Pattern.compile(typeExtractPattern);
        Matcher matcher = pattern.matcher(reqStr);
        if (!matcher.matches()) {
            return null;
        }
        String mesTypeStr = matcher.group(TYPE_GROUP);
        return valueOf(mesTypeStr);
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType().toString();
    }


}
