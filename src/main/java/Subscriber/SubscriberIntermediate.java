// 
// Decompiled by Procyon v0.5.36
// 

package Subscriber;

import Message.AMessage;
import java.io.IOException;
import Utils.PropertiesUtil;
import ContentServer.PeerListener;
import java.io.PrintWriter;
import java.net.Socket;
import ContentServer.IPeerHandler;

public class SubscriberIntermediate implements IPeerHandler
{
    private Socket socket;
    private PrintWriter out;
    private PeerListener listener;
    private ASubscriber subscriber;
    
    public SubscriberIntermediate(final ASubscriber subscriber) throws IOException {
        this.subscriber = subscriber;
        final String defaultHost = PropertiesUtil.getInstance().getProperty("defaultHost");
        final int defaultPort = Integer.parseInt(PropertiesUtil.getInstance().getProperty("defaultSubPort"));
        this.socket = new Socket(defaultHost, defaultPort);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
    }
    
    public SubscriberIntermediate(final ASubscriber subscriber, final String host, final int port) throws IOException {
        this.subscriber = subscriber;
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        (this.listener = new PeerListener(this, this.socket)).start();
    }
    
    @Override
    public void delegateMessage(final AMessage message) {
        this.subscriber.receivedMessage(message);
    }
    
    @Override
    public void sendMessage(final AMessage message) throws IOException {
        final String json = message.serialize();
        this.out.println(json);
        this.out.flush();
    }
}
