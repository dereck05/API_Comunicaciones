// 
// Decompiled by Procyon v0.5.36
// 

package Subscriber;

import Message.AMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ASubscriber
{
    private String id;
    private boolean connected;
    protected List<String> subscriptions;
    protected SubscriberIntermediate intermediate;
    
    public ASubscriber() throws IOException {
        this.subscriptions = new ArrayList<String>();
        this.intermediate = new SubscriberIntermediate(this);
    }
    
    public ASubscriber(final String host, final int port) throws IOException {
        this.subscriptions = new ArrayList<String>();
        this.intermediate = new SubscriberIntermediate(this, host, port);
    }
    
    public boolean isConnected() {
        return this.connected;
    }
    
    public void setConnected(final boolean connected) {
        this.connected = connected;
    }
    
    public String getId() {
        return this.id;
    }
    
    protected void setId(final String id) {
        this.id = id;
    }
    
    public boolean addSubscription(final String subscription) {
        if (!this.subscriptions.contains(subscription)) {
            this.subscriptions.add(subscription);
            return true;
        }
        return false;
    }
    
    public List<String> getSubscriptions() {
        return this.subscriptions;
    }
    
    public void removeSubscription(final String subscription) {
        this.subscriptions.remove(subscription);
    }
    
    public abstract void subscribe(final String p0);
    
    public abstract void unsubscribe(final String p0);
    
    public abstract void askForTopics();
    
    public abstract void sendMessage(final AMessage p0);
    
    public abstract void receivedMessage(final AMessage p0);
}
