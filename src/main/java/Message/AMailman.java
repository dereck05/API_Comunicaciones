

package Message;

public class AMailman
{
    private AMessage message;
    
    public AMailman() {
    }
    
    public AMailman(final AMessage message) {
        this.message = message;
    }
    
    public AMessage getMessage() {
        return this.message;
    }
    
    public void setMessage(final AMessage message) {
        this.message = message;
    }
}
