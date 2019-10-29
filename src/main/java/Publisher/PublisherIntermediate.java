// 
// Decompiled by Procyon v0.5.36
// 

package Publisher;

import Message.AMessage;
import java.io.IOException;
import Utils.PropertiesUtil;
import ContentServer.PeerListener;
import java.io.PrintWriter;
import java.net.Socket;
import ContentServer.IPeerHandler;

public class PublisherIntermediate implements IPeerHandler
{
    private Socket socket;
    private PrintWriter out;
    private PeerListener listener;
    private APublisher publisher;
    
    public PublisherIntermediate(final APublisher publisher) throws IOException {
        this.publisher = publisher;
        final String defaultHost = PropertiesUtil.getInstance().getProperty("defaultHost");
        final int defaultPort = Integer.parseInt(PropertiesUtil.getInstance().getProperty("defaultPubPort"));
        this.socket = new Socket(defaultHost, defaultPort);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
        this.out.println(publisher.getTopic());
        this.out.flush();
    }
    
    public PublisherIntermediate(final APublisher publisher, final String host, final int port) throws IOException {
        this.publisher = publisher;
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
        this.out.println(publisher.getTopic());
        this.out.flush();
    }
    
    @Override
    public void delegateMessage(final AMessage message) {
        this.publisher.receivedMessage(message);
    }
    
    @Override
    public void sendMessage(final AMessage message) throws IOException {
        final String json = message.serialize();
        this.out.println(json);
        this.out.flush();
    }
}
