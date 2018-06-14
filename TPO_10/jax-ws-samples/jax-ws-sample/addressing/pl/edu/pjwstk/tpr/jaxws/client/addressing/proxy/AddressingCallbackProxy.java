package pl.edu.pjwstk.tpr.jaxws.client.addressing.proxy;

import com.sun.xml.internal.ws.developer.WSBindingProvider;
import pl.edu.pjwstk.tpr.jaxws.client.addressing.AddressingCallback;
import pl.edu.pjwstk.tpr.jaxws.client.addressing.AddressingCallbackService;
import pl.edu.pjwstk.tpr.jaxws.client.common.ProxyBase;

public class AddressingCallbackProxy extends ProxyBase implements AddressingCallback {
    
    private AddressingCallbackService _service; 
    private AddressingCallback _port;

	public void replyAddressingCallback(String request) {
    	getPort().replyAddressingCallback(request);
    }
    
    protected WSBindingProvider getBindingProvider() {
		return (WSBindingProvider)getPort();
	}
    
    private AddressingCallback getPort() {
        if (_service == null) {
            _service = new AddressingCallbackService();
        }
        if (_port == null) {
        	_port = _service.getAddressingCallbackPort();
        }
        return _port;
    }
}