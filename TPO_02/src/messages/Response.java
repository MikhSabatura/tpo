package messages;

import exceptions.Assignment02Exception;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response extends Message {

    private final static String CONTENT_GROUP = "CONTENT";
    private final static String RESPONSE_PATTERN = TYPE_PATTERN_NAMED + "(\\s+(?<" + CONTENT_GROUP + ">" + ELEMENT_PATTERN + "(\\s+" + ELEMENT_PATTERN + ")*))?\\s*";

    private String content;//todo: rename

    public Response(MessageType type) {
        super(type);
    }

    public Response(MessageType type, String content) {
        super(type);
        this.content = content;
    }

    public static Response parseResponse(String str) {
        if(!validateResponse(str)) {
            return new Response(MessageType.ILLEGAL);
        }
        MessageType type = parseMessageType(str);
        String content = parseContent(str);
        return new Response(type, content);
    }

    private static String parseContent(String str) {
        Pattern pattern = Pattern.compile(RESPONSE_PATTERN);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new Assignment02Exception("ILLEGAL RESPONSE");
        }
        return matcher.group(CONTENT_GROUP) != null ? matcher.group(CONTENT_GROUP) : "";
    }

    private static boolean validateResponse(String str) {
        return Pattern.compile(RESPONSE_PATTERN).matcher(str).matches();
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return super.toString() + " " + getContent();
    }
}
