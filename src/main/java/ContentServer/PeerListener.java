
package ContentServer;

import Message.AMessage;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.Socket;

public class PeerListener extends Thread
{
    private Socket socket;
    private InputStream input;
    private BufferedReader reader;
    private IPeerHandler handler;
    
    public PeerListener(final IPeerHandler handler, final Socket socket) throws IOException {
        this.handler = handler;
        this.socket = socket;
        this.input = this.socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(this.input));
    }
    
    @Override
    public void run() {
        String content = "";
        while (true) {
            try {
                content = this.reader.readLine();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            final String json = content;
            final AMessage message = AMessage.deserialize(json);
            this.handler.delegateMessage(message);
        }
    }
}
