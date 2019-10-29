package ContentServer;


import java.net.Socket;
import java.io.IOException;
import Utils.PropertiesUtil;
import java.net.ServerSocket;

public class NewSubscribersListener extends Thread
{
    private AContentServer contentServer;
    private ServerSocket serverSocket;
    
    public NewSubscribersListener(final AContentServer contentServer) throws IOException {
        this.contentServer = contentServer;
        final int defaultPort = Integer.parseInt(PropertiesUtil.getInstance().getProperty("defaultSubPort"));
        this.serverSocket = new ServerSocket(defaultPort);
    }
    
    public NewSubscribersListener(final AContentServer contentServer, final int defaultPort) throws IOException {
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
                        this.contentServer.registerSubscriber(socket);
                    }
                }
                catch (IOException ex) {
                    continue Label_0002_Outer;
                }
                //continue;
            }
        }
    }
}
