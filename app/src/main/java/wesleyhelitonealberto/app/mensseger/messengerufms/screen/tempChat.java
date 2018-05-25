package wesleyhelitonealberto.app.mensseger.messengerufms.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import wesleyhelitonealberto.app.mensseger.messengerufms.R;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.Chat;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.Contacts;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.ControlOfContacts;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.UtilsNet;


public class tempChat extends AppCompatActivity {

    // por enquanto os testes estão ainda sendo vistos no LogCat nas linhas System.out:
    // não consegui transferir o scrollView para as demais classes propagando

    Chat chat;
    Contacts contact;
    Contacts contact2;
    Button btnEnter;
    ScrollView scrollServ;
    ScrollView scrollCli;
    EditText textEnter;
    ArrayList<Contacts> cont;
    TextView text1;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_chat);

        btnEnter = findViewById(R.id.buttonEnterr);
        scrollServ = findViewById(R.id.scrollServ);
        scrollCli = findViewById(R.id.scrollCli);
        textEnter = findViewById(R.id.enterr);
        text1 = findViewById(R.id.textOnScrollServ);
        text2 = findViewById(R.id.textOnScrollCli);

        //block start
        // ------------------------------------------------------------------------------------------------------------------
            // aqui deve ser arranjada alguma forma de capturar o ip do próprio aparelho
        // ------------------------------------------------------------------------------------------------------------------
        /*WifiManager wifiMan = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        String ip1 = "192.168.83.2";
        String ip2  = getLocalIpAddress();

        System.out.println("\n\n---||| " + "* " + ip2);
        System.out.println("\n ----------------------------> ip: "+ ip);*/

        // ------------------------------------------------------------------------------------------------------------------
        // aqui deve ser arranjada alguma forma de capturar o ip do próprio aparelho. Deve ser usada a mesma expressão em backGround.LocalServerUdp
        // para retorna a chamada do client udp com o ip do servidor que bateu
        // ------------------------------------------------------------------------------------------------------------------
        // end block
        // ------------------------------------------------------------------------------------------------------------------
        // a info deve ser att em contact.setIp e contact2.setIp para poder realizar os testes
        // ------------------------------------------------------------------------------------------------------------------

        String ip1 = UtilsNet.getIPAddress(true);
            contact = new Contacts();
            contact.setIndetification("antonio");

            // setIp linha de baixo
            contact.setIp(ip1);
            System.out.println("\n------\n" + contact.getIp() + "\n----------\n");
            contact.setLastMsg("algo");
        ControlOfContacts.addContact(contact);

            contact2 = new Contacts();
            contact2.setIndetification("wesley");
            contact2.setIp("192.168.0.16");
            System.out.println("\n------\n" + contact2.getIp() + "\n----------\n");
            contact2.setLastMsg("algo2");

            ControlOfContacts.addContact(contact2);

        ControlOfContacts.printt();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text1.setText(text1.getText() + "entre com opção:::: ");
                String option = textEnter.getText().toString();
                switch (option) {
                    case "s":
                        System.out.println("start");
                        chat = new Chat();
                        break;
                    case "o":
                        text1.setText(text1.getText() + "\n--------------->\n one\n------------>\n");
                        cont = ControlOfContacts.getAllContacts();
                        System.out.println("ip: " + cont.get(1).getIp());
                        chat.openContactToCommunication(cont.get(1));
                        Log.i("\n\n--------------> ", " " + cont.get(1).getIp());
                        textEnter.setText("");
                        break;
                    case "c":
                        if (chat != null) {
                                chat.close();
                                chat = null;
                        }
                        textEnter.setText("");
                        break;
                    case "list":
                        cont = ControlOfContacts.getAllContacts();
                        for (Contacts contac : cont) {
                            System.out.println(contac.getIndetification());
                            System.out.println(contac.getIp());
                            System.out.println(contac.getLastMsg());
                            System.out.println(String.valueOf(cont.size()));
                        }
                        textEnter.setText("");
                        break;
                    default:
                        textEnter.setText("");
                        break;
                }

            }
        });
    }
}
