
package ContentServer;

import Message.AMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class PublisherHandler implements IPeerHandler
{
    private String topic;
    private Socket socket;
    private PrintWriter writer;
    private PeerListener listener;
    private AContentServer server;
    
    public PublisherHandler(final AContentServer server, final Socket socket, final String topic) throws IOException {
        this.topic = topic;
        this.server = server;
        this.socket = socket;
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    @Override
    public void delegateMessage(final AMessage message) {
        this.server.processPubMessage(message, this);
    }
    
    @Override
    public void sendMessage(final AMessage message) throws IOException {
        final String json = message.serialize();
        this.writer.println(json);
        this.writer.flush();
    }
}
