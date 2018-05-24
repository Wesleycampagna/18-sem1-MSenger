package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Chat {
    ArrayList<Contacts> contacts;
    Thread serverRecvMsg;
    Thread serverRecvConnection;
    Thread requestConnection;
    ServerSocket serverSocketTCP;
    DatagramSocket serverSocketUDP;
    DatagramSocket clientSocket;
    private int port;

    public Chat() {
        port = 7196;
        try {
            serverSocketTCP = new ServerSocket(port);
            serverSocketUDP = new DatagramSocket(port);
            clientSocket = new DatagramSocket();

        } catch (IOException e) {
            System.out.println("não deu socket server");
        }
        // start thread para requerir aparelhos conectados
        requestConnection = new Thread (new ClientUdp(clientSocket, port));
        requestConnection.start();
        // start thread do servidor UDP do aparelho local
        serverRecvMsg = new Thread (new LocalServerTcp(serverSocketTCP));
        serverRecvMsg.start();
        // start thread do servidor UDP do aparelho local
        serverRecvConnection = new Thread(new LocalServerUdp(serverSocketUDP));
        serverRecvConnection.start();
    }

    public void openContactToCommunication(Contacts contact) {
        System.out.println("----\nhere\n-----");
        ClientOnConversation.setContact(contact);
        StartChat myDevice = new StartChat();
        myDevice.setConnection(contact.getIp(), port);
        // estabelecida a conexão. Só Conversar

        // o ponto de parada deve ser setado com o 'x' na tela da msg
        String msg = "";
        do {
            System.out.println("return");
            //capture of textView
            //msg = scan.next();
            if (msg.equals("fim"))
                break;
            myDevice.sendMsg(msg);
            // System.out.println("\nenviou mensagem");
        } while (true);
        myDevice.closeSocket();
        ClientOnConversation.setFalseChatOpen();
        System.out.println("<< close >>");
    }


    @SuppressWarnings("deprecation")
    public void close() throws IOException {
        StateApp.setIsFim();
        boolean fim = false;
        serverRecvMsg.stop();
        serverSocketTCP.close();
        while (!fim) {
            try {
                if (!serverRecvConnection.isAlive() && !requestConnection.isAlive() && !serverRecvMsg.isAlive())
                    fim = true;
                if (requestConnection.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("clienteUDP vivo...");
                }
                else if (serverRecvConnection.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("serverUDP vivo...");
                }else {
                    clientSocket.close();
                    serverSocketUDP.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
