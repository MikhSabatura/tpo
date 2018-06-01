package participants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Requester {
    public static void main(String[] args) throws NamingException {
        Context sdf = new InitialContext();
        sdf.close();
        System.out.println("everything ok");
    }
}
