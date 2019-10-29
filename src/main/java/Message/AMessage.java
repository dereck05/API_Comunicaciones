

package Message;

import Message.JsonManagement.AMessageManager;

public abstract class AMessage
{
    private String topic;
    
    public AMessage(final String topic) {
        this.topic = topic;
    }
    
    public String getTopic() {
        return this.topic;
    }
    
    public void setTopic(final String topic) {
        this.topic = topic;
    }
    
    public String serialize() {
        final AMessageManager manager = AMessageManager.getInstance();
        return manager.getGsonObject().toJson(new AMailman(this));
    }
    
    public static AMessage deserialize(final String json) {
        final AMessageManager manager = AMessageManager.getInstance();
        return manager.getGsonObject().fromJson(json, AMailman.class).getMessage();
    }
}
