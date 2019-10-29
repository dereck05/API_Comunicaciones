
package ContentServer;

import java.io.IOException;
import Message.AMessage;

public interface IPeerHandler
{
    void delegateMessage(final AMessage p0);
    
    void sendMessage(final AMessage p0) throws IOException;
}
