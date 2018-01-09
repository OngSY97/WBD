package my.edu.tarc.wbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.edu.tarc.wbd.Model.DataSource;
import my.edu.tarc.wbd.Model.DatabaseHelper;
import my.edu.tarc.wbd.Model.personalAccount;

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
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean condition =  saveRegistration(v);
                if (condition == true) {
                    startActivity(new Intent(Registration.this, Login.class));
                }
            }
        });
    }

    public boolean saveRegistration(View v) {
        boolean condition = false;
        personalAccount details = new personalAccount();

        String firstName, lastName, emailAddress, password, confirmedPassword;
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        emailAddress = editTextEmailAddress.getText().toString();
        password = editTextPasswordRegister.getText().toString();
        confirmedPassword = editTextConfirmedPassword.getText().toString();
        while (condition != true) {
            if (firstName.isEmpty()) {
                editTextFirstName.setError(getString(R.string.error_empty));
                return condition;
            } else if (Character.isDigit(firstName.charAt(0))) {
                editTextFirstName.setError(getString(R.string.error_firstname));
                return condition;
            }

            if (lastName.isEmpty()) {
                editTextLastName.setError(getString(R.string.error_empty));
                return condition;
            }

            if (emailAddress.isEmpty()) {
                editTextEmailAddress.setError(getString(R.string.error_empty));
                return condition;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                editTextEmailAddress.setError(getString(R.string.error_email));
                return condition;
            }

            if (password.isEmpty()) {
                editTextPasswordRegister.setError(getString(R.string.error_empty));
                return condition;
            } else if (password.length() <= 5 || password.length() >= 13) {
                editTextPasswordRegister.setError(getString(R.string.error_password));
                return condition;
            } else if (confirmedPassword.isEmpty()) {
                editTextConfirmedPassword.setError(getString(R.string.error_empty));
                return condition;
            } else if (!confirmedPassword.equals(password)) {
                editTextConfirmedPassword.setError(getString(R.string.error_invalidPassword));
                return condition;
            }
        }
          //  condition = true;
            String name = firstName.toString().concat(" ").concat(lastName.toString());

            details.setEmail(emailAddress);
            details.setName(name);
            details.setPassword(confirmedPassword);

            DataSource personalDetails = new DataSource(this);
            boolean isInserted = personalDetails.insertData(details);
            if(isInserted== true) {
                condition = true;
                Toast.makeText(this,"Data Inserted",Toast.LENGTH_LONG).show();
            }
            else {
                condition = false;
                Toast.makeText(this,"Data not Inserted",Toast.LENGTH_LONG).show();            }

            return condition;
        }


}
