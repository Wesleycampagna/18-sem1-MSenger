package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ClientUdp implements Runnable {
    DatagramSocket clientSocket;
    private int port;
    private int size_packet;

    public ClientUdp(DatagramSocket clientSocket, int port) {
        this.clientSocket = clientSocket;
        this.port = port;
        size_packet = 32;
    }

    @Override
    public void run() {

        do{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            String isOn = "isOn";
            sendMsg(isOn);
        } while (!StateApp.isFim()) ;
        System.out.println("----------> clienteUDP: saindo...");
    }

    private void sendMsg(String txt) {
        try {

            clientSocket.setSoTimeout(200);

            DatagramPacket sendToAllServers;

            sendToAllServers = new DatagramPacket(txt.getBytes(), txt.length(),
                    InetAddress.getByName("255.255.255.255"), port);

            clientSocket.send(sendToAllServers);

            DatagramPacket response = new DatagramPacket(new byte[size_packet], size_packet);

            clientSocket.receive(response);
            // atualiza interface conforme as pessoas que estão abertas.
            String data = new String(response.getData(), 0, response.getLength());
            //System.out.println(("[clientUDP] rcv: " + data));

        } catch (SocketTimeoutException e) {
            // clientSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
