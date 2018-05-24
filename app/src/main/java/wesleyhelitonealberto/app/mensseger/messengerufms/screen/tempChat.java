package wesleyhelitonealberto.app.mensseger.messengerufms.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import wesleyhelitonealberto.app.mensseger.messengerufms.R;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.Chat;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.Contacts;
import wesleyhelitonealberto.app.mensseger.messengerufms.backGround.ControlOfContacts;


public class tempChat extends AppCompatActivity {

    Chat chat;
    Contacts contact;
    Contacts contact2;
    Button btnEnter;
    ScrollView scrollV;
    EditText textEnter;
    ArrayList<Contacts> cont;
    String text = "";
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_chat);

        btnEnter = findViewById(R.id.buttonEnter);
        scrollV = findViewById(R.id.scrollTexts);
        textEnter = findViewById(R.id.enter);
        texto = findViewById(R.id.textOnScroll);

        contact = new Contacts();
        contact.setIndetification("wesley");
        contact.setIp("10.6.127.161");
        contact.setLastMsg("algo");

        ControlOfContacts.addContact(contact);

        contact2 = new Contacts();
        contact2.setIndetification("antonio");
        contact2.setIp("10.6.127.161");
        contact2.setLastMsg("sei la");

        ControlOfContacts.addContact(contact2);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTextOnScroll("entre com opção:::: ");
                String option = textEnter.getText().toString();
                switch (option) {
                    case "start":
                        setTextOnScroll("start");
                        chat = new Chat();
                        break;
                    case "one":
                        setTextOnScroll("\n--------------->\n one\n------------>\n");
                        cont = ControlOfContacts.getAllContacts();
                        System.out.println("ip: " + cont.get(1).getIp().toString());
                        chat.openContactToCommunication(cont.get(1));
                        break;
                    case "close":
                        if (chat != null) {
                            try {
                                chat.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "list":
                        cont = ControlOfContacts.getAllContacts();
                        for (Contacts contac : cont) {
                            setTextOnScroll(contac.getIndetification());
                            setTextOnScroll(contac.getIp());
                            setTextOnScroll(contac.getLastMsg());
                            setTextOnScroll(String.valueOf(cont.size()));
                        }
                        break;
                    default:
                        break;
                }

            }
        });
    }

    public void setTextOnScroll(String txt){
        text += txt + "\n";
        texto.setText(text);
    }
}
