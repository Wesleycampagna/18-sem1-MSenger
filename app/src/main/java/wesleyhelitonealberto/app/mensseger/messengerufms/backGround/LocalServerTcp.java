package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServerTcp implements Runnable {
    private String textFull;
    private static ServerSocket socketServerTCP;
    private int SIZE_MSG_MAX = 32;

    public LocalServerTcp(ServerSocket socketServerTCP) {
        LocalServerTcp.socketServerTCP = socketServerTCP;
    }

    public LocalServerTcp() {
    }

    @Override
    public void run() {
        whi: while (true) {
            Socket socketClient = null;
            if (Thread.currentThread().getName().equals("tcp")) {
                if (StateApp.isFim())
                    break whi;
                else {
                    try {
                        Thread.sleep(2000);
                        //System.out.println("while vivo() <-----" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // to TCP
                    // if (!StateApp.isFim())
                    try {
                        socketClient = socketServerTCP.accept();
                    } catch (IOException e) {
                        System.out.println("fdp");
                    }
                    // if cliente estiver nesta conversa: attChat.

                    // else - armazena banco de dados e fecha.
                    if (!StateApp.isFim())
                        saveData(socketClient);
                }
            }
            if (Thread.currentThread().getName().equals("control")) {
                try {
                    Thread.sleep(2000);
                    //System.out.println("while vivo1 <-----" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (StateApp.isFim()) {
                    System.out.println("socketServerTCP - " + socketServerTCP);
                    System.out.println("socketClient - " + socketClient);

                    try {
                        if (socketClient != null)
                            socketClient.close();

                        if (socketServerTCP != null)
                            socketServerTCP.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    System.out.println("socketServerTCP foi fechado");
                    break whi;
                }
            }
        }
        System.out.println("----------> LocalServer: saindo..." + Thread.currentThread().getName());
    }

    private void saveData(final Socket socketClient){
            System.out.println("new cliente");
            // pegar ip cliente
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        DataInputStream in = new DataInputStream(socketClient.getInputStream());
                        // pegar texto cliente
                        byte[] recv = new byte[SIZE_MSG_MAX];
                        int size;
                        textFull = "";

                        while ((size = in.read(recv)) != -1) {

                            String msg = (new String(recv, 0, size));

                            System.out.println("msgTxt: " + msg + "#");
                            textFull += msg + "\n";

                            recv = new byte[SIZE_MSG_MAX];
                        }

                        System.out.println("\n\n--------\nText: " + textFull + "\n---------\n\n");
                        // socketClient.close();
                    } catch (IOException e) {
                        System.out.println("[LocalServerTcp] SaveData: não capturou socket in DataInput");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
}