

package ContentServer;

import Message.AMessage;
import java.util.Iterator;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;

public abstract class AContentServer
{
    protected Hashtable<String, List<SubscriberHandler>> subscriptions;
    protected List<SubscriberHandler> subscribers;
    protected List<PublisherHandler> publishers;
    protected NewSubscribersListener subConnListener;
    protected NewPublishersListener pubConnListener;
    
    public AContentServer() throws IOException {
        this.subscriptions = new Hashtable<String, List<SubscriberHandler>>();
        this.subscribers = new ArrayList<SubscriberHandler>();
        this.publishers = new ArrayList<PublisherHandler>();
        this.subConnListener = new NewSubscribersListener(this);
        this.pubConnListener = new NewPublishersListener(this);
        this.subConnListener.start();
        this.pubConnListener.start();
    }
    
    public boolean registerPublisher(final Socket socket, final String topic) throws IOException {
        final PublisherHandler newPublisher = new PublisherHandler(this, socket, topic);
        if (!this.publishers.contains(newPublisher) && !this.subscriptions.keySet().contains(topic)) {
            this.publishers.add(newPublisher);
            this.subscriptions.put(topic, new ArrayList<SubscriberHandler>());
            this.acceptPubConnection(newPublisher);
            return true;
        }
        this.denyPubConnection(newPublisher);
        return false;
    }
    
    public void removePublisher(final PublisherHandler publisher) {
        this.publishers.remove(publisher);
        this.subscriptions.remove(publisher.getTopic());
    }
    
    public boolean registerSubscriber(final Socket socket) throws IOException {
        final SubscriberHandler newSubscriber = new SubscriberHandler(this, socket);
        if (!this.subscribers.contains(newSubscriber)) {
            this.subscribers.add(newSubscriber);
            this.acceptSubConnection(newSubscriber);
            return true;
        }
        this.denySubConnection(newSubscriber);
        return false;
    }
    
    public void removeSubscriber(final SubscriberHandler subscriber) {
        this.subscribers.remove(subscriber);
        for (final String topic : this.subscriptions.keySet()) {
            this.subscriptions.get(topic).remove(subscriber);
        }
    }
    
    public boolean registerSubscription(final String topic, final SubscriberHandler subscriber) {
        if (!this.subscriptions.get(topic).contains(subscriber)) {
            this.subscriptions.get(topic).add(subscriber);
            return true;
        }
        return false;
    }
    
    public void removeSubscription(final String topic, final SubscriberHandler subscriber) {
        this.subscriptions.get(topic).remove(subscriber);
    }
    
    public void broadcastMessagePub(final AMessage message) throws IOException {
        for (final PublisherHandler handler : this.publishers) {
            handler.sendMessage(message);
        }
    }
    
    public void broadcastMessageSub(final AMessage message, final String topic) throws IOException {
        for (final SubscriberHandler handler : this.subscriptions.get(topic)) {
            handler.sendMessage(message);
        }
    }
    
    public abstract void processSubMessage(final AMessage p0, final SubscriberHandler p1);
    
    public abstract void processPubMessage(final AMessage p0, final PublisherHandler p1);
    
    public abstract void broadcastTopics(final SubscriberHandler p0);
    
    public abstract void acceptPubConnection(final PublisherHandler p0);
    
    public abstract void acceptSubConnection(final SubscriberHandler p0);
    
    public abstract void denyPubConnection(final PublisherHandler p0);
    
    public abstract void denySubConnection(final SubscriberHandler p0);
}
