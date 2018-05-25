package wesleyhelitonealberto.app.mensseger.messengerufms.screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wesleyhelitonealberto.app.mensseger.messengerufms.R;

public class ChatScreen extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        btn = findViewById(R.id.buttonToTemp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temporarioChat = new Intent(view.getContext(), tempChat.class);
                startActivity(temporarioChat);
            }
        });
    }
}
