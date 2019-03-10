package e.rkkee.easypark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView fgt,nwt;
    EditText use,pse;
    Button lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fgt = findViewById(R.id.fg);
        nwt = findViewById(R.id.nw);
        use = findViewById(R.id.us);
        pse = findViewById(R.id.pas);
        lg = findViewById(R.id.log);



        lg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (use.getText().toString().equals("admin") && pse.getText().toString().equals("1234")) {
                    Intent intent = new Intent(MainActivity.this, firstpg.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MainActivity.this, "login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
