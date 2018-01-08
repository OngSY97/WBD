package my.edu.tarc.wbd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPasswordRegister, editTextConfirmedPassword;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);
        editTextPasswordRegister = (EditText) findViewById(R.id.editTextPasswordRegister);
        editTextConfirmedPassword = (EditText) findViewById(R.id.editTextConfirmedPassword);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
    }

    public void saveRegistration(View v) {

        String firstName, lastName, emailAddress, password, confirmedPassword;
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        emailAddress = editTextEmailAddress.getText().toString();
        password = editTextPasswordRegister.getText().toString();
        confirmedPassword = editTextConfirmedPassword.getText().toString();

        if (firstName.isEmpty()) {
            editTextFirstName.setError(getString(R.string.error_empty));
            return;
        } else if (Character.isDigit(firstName.charAt(0))) {
            editTextFirstName.setError(getString(R.string.error_firstname));
            return;
        }

        if (lastName.isEmpty()) {
            editTextLastName.setError(getString(R.string.error_empty));
            return;
        }

        if(emailAddress.isEmpty()){
            editTextEmailAddress.setError(getString(R.string.error_empty));
            return;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextEmailAddress.setError(getString(R.string.error_email));
            return;
        }

        if(password.isEmpty()){
            editTextPasswordRegister.setError(getString(R.string.error_empty));
            return;
        }else if(password.length()<=5 || password.length()>=13){
            editTextPasswordRegister.setError(getString(R.string.error_password));
            return;
        }else if (confirmedPassword.isEmpty()){
            editTextConfirmedPassword.setError(getString(R.string.error_empty));
            return;
        }else if (!confirmedPassword.equals(password)){
            editTextConfirmedPassword.setError(getString(R.string.error_invalidPassword));
            return;
        }
        myDb.getAllData();
    String name = firstName.toString().concat(" ").concat(lastName.toString());

        buttonSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  boolean isInserted = myDb.InsertData(emailAddress, name, password );
                       // if(isInserted == true)
                        //    Toast.makeText(Registration.this,"Data Inserted", Toast.LENGTH_LONG).show();
                        //else
                         //   Toast.makeText(Registration.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


}