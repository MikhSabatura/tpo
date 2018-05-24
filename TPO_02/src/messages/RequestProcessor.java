package messages;

import exceptions.Assignment02Exception;

import java.math.BigDecimal;

public class RequestProcessor {

    public static Response processRequest(Request request) {
        if (request.getType() == MessageType.ILLEGAL) {
            return new Response(MessageType.ILLEGAL, "REQUEST");
        } else if (request.getType() == MessageType.ADD) {
            boolean isNumeric = request.getElements().parallelStream()
                    .allMatch(Message::isNumber);
            if (isNumeric) {
                return processNumericAdd(request);
            } else {
                return processStringAdd(request);
            }
        } else {
            return processEcho(request);
        }
    }

    private static Response processEcho(Request request) {
        if (request.getType() != MessageType.ECHO) {
            throw new Assignment02Exception();
        }
        StringBuilder strBuilder = new StringBuilder();
        request.getElements().stream()
                .forEach(e -> strBuilder.append(e).append(" "));
        return new Response(MessageType.ECHO, strBuilder.toString().trim());
    }

    private static Response processStringAdd(Request request) {
        if (request.getType() != MessageType.ADD) {
            throw new Assignment02Exception();
        }
        StringBuilder strBuilder = new StringBuilder();
        request.getElements().stream()
                .forEach(e -> strBuilder.append(e));
        return new Response(MessageType.ADD, strBuilder.toString());
    }

    private static Response processNumericAdd(Request request) {
        if (request.getType() != MessageType.ADD) {
            throw new Assignment02Exception();
        }
        BigDecimal sum = request.getElements().parallelStream()
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        return new Response(MessageType.ADD, sum.toString());
    }


}
