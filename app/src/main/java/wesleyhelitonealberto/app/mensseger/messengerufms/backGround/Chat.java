package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Chat {

    private ArrayList<Contacts> contacts;

    private Thread serverRecvMsg;
    private Thread serverRecvConnection;
    private Thread requestConnection;
    private Thread control;

    private ServerSocket serverSocketTCP;
    private DatagramSocket serverSocketUDP;
    private DatagramSocket clientSocket;
    private int port;

    public Chat() {
        port = 7197;
        StateApp.NotIsFim();

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

        // start thread do servidor TCP do aparelho local
        serverRecvMsg = new Thread (new LocalServerTcp(serverSocketTCP));
        serverRecvMsg.setName("tcp");
        serverRecvMsg.start();

        //Thread de controle
        control = new Thread (new LocalServerTcp());
        control.setName("control");
        control.start();

        // start thread do servidor UDP do aparelho local
        serverRecvConnection = new Thread(new LocalServerUdp(serverSocketUDP));
        serverRecvConnection.start();
    }

    public void openContactToCommunication(final Contacts contact) {
        ClientOnConversation.setContact(contact);

        final String textos [] = {"mensagem1. cambio..", "alguem do outro lado? cambio", "fim"};

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StartChat myDevice = new StartChat();
                final Socket clientSocket;
                int cont = 0;
                try {
                    clientSocket = new Socket(contact.getIp(), port);
                    myDevice.setConnection(clientSocket);

                    String msg = "";
                    do{

                        msg = textos[cont];
                        cont ++;
                        //pensar aqui ainda

                        if (msg.equals("fim") || myDevice.getStatus())
                            break;

                        myDevice.sendMsg(msg);

                    }while (true);

                    clientSocket.close();
                    ClientOnConversation.setFalseChatOpen();
                    System.out.println("<< close >>\n");


                } catch (UnknownHostException e) {
                    System.out.println("host desconhecido");
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                System.out.println("<< close >>\n");
            }
        }).start();

    }

    public void close() {
        StateApp.setIsFim();
        boolean fim = false;

        while (!fim) {
            try {
                if (!serverRecvConnection.isAlive() && !requestConnection.isAlive() && !serverRecvMsg.isAlive())
                    fim = true;

                if (requestConnection.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("clienteUDP vivo...");
                } else if (serverRecvConnection.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("serverUDP vivo...");
                } else {
                    clientSocket.close();
                    serverSocketUDP.close();
                    if (serverRecvMsg.isAlive()) {
                        System.out.println("server tcp is alive..");
                        Thread.sleep(1000);
                        try {
                            serverSocketTCP.close();
                        } catch (IOException e) {
                            System.out.println("cai fora\n\n\n");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
