package requests;

public class RandomRequest implements IRequest {

    private String senderName;

    public RandomRequest(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public String getSenderName() {
        return null;
    }
}
