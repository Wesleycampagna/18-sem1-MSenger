package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class StartChat {
    private Socket clientSocket;
    private DataOutputStream out;
    private boolean status;

    public void setConnection(Socket clienteSocket) throws UnknownHostException, IOException {
        status = false;
        try {
            out = new DataOutputStream(clienteSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("DataImputStream");
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            System.out.println("msgToSend: " + msg + "#");
            out.write(msg.getBytes(), 0, msg.length());
            out.flush();
        } catch (IOException e) {
            System.out.println("erro escrita");
            e.printStackTrace();
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus() {
        status = true;
    }
}