 

package ContentServer;

import java.net.Socket;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import Utils.PropertiesUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.ServerSocket;

public class NewPublishersListener extends Thread
{
    private AContentServer contentServer;
    private ServerSocket serverSocket;
    private InputStream input;
    private BufferedReader reader;
    
    public NewPublishersListener(final AContentServer contentServer) throws IOException {
        this.contentServer = contentServer;
        final int defaultPort = Integer.parseInt(PropertiesUtil.getInstance().getProperty("defaultPubPort"));
        this.serverSocket = new ServerSocket(defaultPort);
    }
    
    public NewPublishersListener(final AContentServer contentServer, final int defaultPort) throws IOException {
        this.contentServer = contentServer;
        this.serverSocket = new ServerSocket(defaultPort);
    }
    
    @Override
    public void run() {
        Socket socket = null;
    Label_0002_Outer:
        while (true) {
            while (true) {
                try {
                    while (true) {
                        socket = this.serverSocket.accept();
                        this.input = socket.getInputStream();
                        this.reader = new BufferedReader(new InputStreamReader(this.input));
                        final String topic = this.reader.readLine();
                        this.contentServer.registerPublisher(socket, topic);
                    }
                }
                catch (IOException ex) {
                    continue Label_0002_Outer;
                }
               
            }
        }
    }
}
