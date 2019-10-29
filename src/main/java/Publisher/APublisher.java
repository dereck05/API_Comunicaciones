

package Publisher;

import Message.AMessage;
import java.io.IOException;

public abstract class APublisher
{
    private String topic;
    private boolean connected;
    protected PublisherIntermediate intermediate;
    
    public APublisher(final String topic) throws IOException {
        this.topic = topic;
        this.intermediate = new PublisherIntermediate(this);
    }
    
    public APublisher(final String topic, final String host, final int port) throws IOException {
        this.topic = topic;
        this.intermediate = new PublisherIntermediate(this, host, port);
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    public boolean isConnected() {
        return this.connected;
    }
    
    public void setConnected(final boolean connected) {
        this.connected = connected;
    }
    
    public abstract void publish(final AMessage p0);
    
    public abstract void receivedMessage(final AMessage p0);
}
