package wesleyhelitonealberto.app.mensseger.messengerufms.backGround;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalServerTcp implements Runnable {
    private String textFull;
    private ServerSocket socketServerTCP;
    private int SIZE_MSG_MAX = 32;

    public LocalServerTcp(ServerSocket socketServerTCP) {
        this.socketServerTCP = socketServerTCP;

    }

    @Override
    public void run() {
        try {
            while (!StateApp.isFim()) {
                System.out.println("while vivo <-----");
                // to TCP
                final Socket socketClient = socketServerTCP.accept();
                // if cliente estiver nesta conversa: attChat.

                // else - armazena banco de dados e fecha.
                saveData(socketClient);

            }
            System.out.println("----------> LocalServer: saindo...");

        } catch (IOException e) {
            System.out.println("[LocalServerTcp] run: algum problem");
            e.printStackTrace();
        }
    }

    private void saveData(final Socket socketClient) {
        System.out.println("new cliente");
        // pegar ip cliente
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("enter here");
                    DataInputStream in = new DataInputStream(socketClient.getInputStream());
                    // pegar texto cliente
                    byte[] recv = new byte[SIZE_MSG_MAX];
                    int size = 0;
                    textFull = "";
                    int count = 0;
                    while (!StateApp.isFim() || ((size = in.read(recv)) != -1)) {
                        count++;
                        String msg = (new String(recv, 0, size));
                        System.out.println("msgTxt: " + msg + "|");
                        textFull += msg + " ";

                        System.out.println("fim[c]: " + count);
                        recv = new byte[SIZE_MSG_MAX];
                    }

                    System.out.println("\n\n--------\nText: " + textFull + "\n---------\n\n");
                    //socketClient.close();
                } catch (IOException e) {
                    System.out.println("[LocalServerTcp] SaveData: não capturou socket in DataInput");
                    e.printStackTrace();
                }
            }
        }).start();

    }
}