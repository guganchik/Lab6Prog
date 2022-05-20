package app;

import data.Request;
import data.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkProvider {
    
    private static final int BUFFER_SIZE = 1024 * 1024;
    
    private final DatagramSocket datagramSocket;

    public NetworkProvider(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
        datagramSocket.setSoTimeout(20);
        System.out.println("=========== Server started on port: " + port + " ===========");
    }
    
    public Request receive() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
            DatagramPacket datagramPacket = new DatagramPacket(buf.array(), buf.array().length);
            
            datagramSocket.receive(datagramPacket);
            SocketAddress client = datagramPacket.getSocketAddress();
            
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));
            Request request = (Request) objectInputStream.readObject();
            request.setClient(client);
            return request;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NetworkProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(NetworkProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void send(SocketAddress client, Response response) {
        ObjectOutputStream objectOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            DatagramPacket datagramPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length);
            datagramPacket.setSocketAddress(client);
            datagramSocket.send(datagramPacket);
        } catch (IOException ex) {
            Logger.getLogger(NetworkProvider.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    

    
}
