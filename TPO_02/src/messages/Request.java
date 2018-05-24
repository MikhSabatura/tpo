package messages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request extends Message {

    private final static String REQUEST_PARAMETER = "(\\s+" + ELEMENT_PATTERN + ")";
    private final static String REQUEST_ECHO_PATTERN = MessageType.ECHO + REQUEST_PARAMETER + "+\\s*";
    private final static String REQUEST_GENERAL_PATTERN = TYPE_PATTERN_NAMED + REQUEST_PARAMETER + "{2,}\\s*";

    private List<String> elements;

    private Request(MessageType type) {
        super(type);
        this.elements = new ArrayList<>();
    }

    private Request(MessageType type, List<String> elements) {
        super(type);
        this.elements = elements;
    }

    //checks if the request is valid
    public static boolean validateRequest(String reqStr) {
        if (Pattern.compile(REQUEST_GENERAL_PATTERN).matcher(reqStr).matches()) {
            return true;
        }
        return Pattern.compile(REQUEST_ECHO_PATTERN).matcher(reqStr).matches();
    }

    public static Request parseRequest(String reqStr) {
        if (!validateRequest(reqStr)) {
            return new Request(MessageType.ILLEGAL);
        }
        MessageType type = parseMessageType(reqStr);
        if (type == null || type == MessageType.ILLEGAL) {
            return new Request(MessageType.ILLEGAL);
        }
        List<String> elements = parseElements(reqStr);
        return new Request(type, elements);
    }

    private static List<String> parseElements(String reqStr) {
        List<String> elements = new ArrayList<>();
        Pattern elPattern = Pattern.compile(ELEMENT_PATTERN);
        Matcher elMatcher = elPattern.matcher(reqStr);
        elMatcher.find();//in order to skip the type parameter
        while (elMatcher.find()) {
            elements.add(elMatcher.group());
        }
        return elements;
    }

    public List<String> getElements() {
        return elements;
    }

    //used when sending the request
    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(super.toString());
        for (Object el : getElements()) {
            strBuilder.append(" ")
                    .append(el);
        }
        return strBuilder.toString();
    }
}
