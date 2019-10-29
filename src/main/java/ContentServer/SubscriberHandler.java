// 
// Decompiled by Procyon v0.5.36
// 

package ContentServer;

import Message.AMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SubscriberHandler implements IPeerHandler
{
    private Socket socket;
    private PrintWriter out;
    private PeerListener listener;
    private AContentServer server;
    private String id;
    
    public SubscriberHandler(final AContentServer server, final Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    @Override
    public void delegateMessage(final AMessage message) {
        this.server.processSubMessage(message, this);
    }
    
    @Override
    public void sendMessage(final AMessage message) throws IOException {
        final String json = message.serialize();
        this.out.println(json);
        this.out.flush();
    }
}
