package e.rkkee.easypark;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonRegister;
    private EditText editTextUser;
    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextContact;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();
            //and open profile activity
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        //initializing views
        editTextFname = findViewById(R.id.editTextFname);
        editTextLname = findViewById(R.id.editTextLname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUser = findViewById(R.id.editTextUser);
        editTextContact = findViewById(R.id.editTextContact);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCPassword = findViewById(R.id.editTextCPassword);
        textViewSignin = findViewById(R.id.textViewSignin);

        buttonRegister = findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts;
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        final String cpassword = editTextCPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(cpassword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.contentEquals(cpassword)){
            Toast.makeText(this,"Password does not match",Toast.LENGTH_SHORT).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            add_data();
                            finish();
                        }
                        else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        progressDialog.dismiss();

    }

    private void add_data(){
        final String firstname = editTextFname.getText().toString().trim();
        final String lastname = editTextLname.getText().toString().trim();
        final int contact = Integer.parseInt(editTextContact.getText().toString());
        final String username = editTextUser.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        Map<String, Object> user = new HashMap<>();
        user.put("first_name", firstname);
        user.put("last_name", lastname);
        user.put("email", email);
        user.put("contact", contact);
        user.put("username", username);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        startActivity(new Intent(getApplicationContext(), Profile.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonRegister){
            registerUser();
        }

    }
}