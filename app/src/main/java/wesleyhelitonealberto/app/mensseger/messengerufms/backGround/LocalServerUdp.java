package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import wesleyhelitonealberto.app.mensseger.messengerufms.screen.tempChat;

public class LocalServerUdp implements Runnable {
    DatagramSocket socketServerUDP;
    private int size_packet = 32;

    public LocalServerUdp(DatagramSocket socketServerUDP) {
        this.socketServerUDP = socketServerUDP;
    }

    public void run() {
        do {
            // to UDP
            DatagramPacket packet = new DatagramPacket(new byte[size_packet], size_packet);
            try {
                socketServerUDP.receive(packet);

                //System.out.println("msg: " + new String(packet.getData(), 0, packet.getLength()));

                returnIfOn(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (!StateApp.isFim());
       System.out.println("----------> serverUDP: saindo...");
    }

    public void returnIfOn(DatagramPacket packet) throws IOException{
        InetAddress ipCliente = packet.getAddress();
        int portClient = packet.getPort();

        String ip = UtilsNet.getIPAddress(true);

        byte[] returnToCliente = ip.getBytes();

        DatagramPacket send;

        send = new DatagramPacket(returnToCliente, returnToCliente.length, ipCliente, portClient);

        socketServerUDP.send(send);
    }
}