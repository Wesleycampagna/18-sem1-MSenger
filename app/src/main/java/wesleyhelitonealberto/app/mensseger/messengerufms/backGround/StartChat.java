package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StartChat {
    DataInputStream in;
    Socket clientSocket;
    DataOutputStream out;

    public void setConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println("erro leitura");
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.write(msg.getBytes(), 0, msg.length());
            System.out.println("msgToSend: " + msg + "**");
            out.flush();
        } catch (IOException e) {
            System.out.println("erro escrita");
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}