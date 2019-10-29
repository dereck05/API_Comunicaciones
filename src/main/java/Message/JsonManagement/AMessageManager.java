
package Message.JsonManagement;

import java.lang.reflect.Type;
import Message.AMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AMessageManager
{
    private static AMessageManager instance;
    private GsonBuilder gsonBilder;
    private Gson gson;
    
    private AMessageManager() {
        (this.gsonBilder = new GsonBuilder()).registerTypeAdapter(AMessage.class, new InterfaceAdapter());
        this.gson = this.gsonBilder.create();
    }
    
    public static AMessageManager getInstance() {
        if (AMessageManager.instance == null) {
            AMessageManager.instance = new AMessageManager();
        }
        return AMessageManager.instance;
    }
    
    public Gson getGsonObject() {
        return this.gson;
    }
}
