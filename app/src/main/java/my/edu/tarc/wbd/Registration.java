package my.edu.tarc.wbd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.edu.tarc.wbd.Model.Account;

public class Registration extends AppCompatActivity {
    private EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPasswordRegister, editTextConfirmedPassword;
    private Button buttonSubmit;
    private ProgressDialog progressDialog;
    private List<Account> accountArrayList;
    private static int count =0;


    boolean condition = false;
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
            saveRegistration(v);
            }
        });
    }

    public void saveRegistration(View v) {

        Account details = new Account();

        String firstName, lastName, emailAddress, password, confirmedPassword;
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        emailAddress = editTextEmailAddress.getText().toString();
        password = editTextPasswordRegister.getText().toString();
        confirmedPassword = editTextConfirmedPassword.getText().toString();

        while (condition != true) {
            if (firstName.isEmpty()) {
                editTextFirstName.setError(getString(R.string.error_empty));
                return ;
            } else if (Character.isDigit(firstName.charAt(0))) {
                editTextFirstName.setError(getString(R.string.error_firstname));
                return ;
            }

            if (lastName.isEmpty()) {
                editTextLastName.setError(getString(R.string.error_empty));
                return ;
            }

            if (emailAddress.isEmpty()) {
                editTextEmailAddress.setError(getString(R.string.error_empty));
                return ;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                editTextEmailAddress.setError(getString(R.string.error_email));
                return ;
            }

            if (password.isEmpty()) {
                editTextPasswordRegister.setError(getString(R.string.error_empty));
                return ;
            } else if (password.length() <= 5 || password.length() >= 13) {
                editTextPasswordRegister.setError(getString(R.string.error_password));
                return ;
            } else if (confirmedPassword.isEmpty()) {
                editTextConfirmedPassword.setError(getString(R.string.error_empty));
                return ;
            } else if (!confirmedPassword.equals(password)) {
                editTextConfirmedPassword.setError(getString(R.string.error_invalidPassword));
                return ;
            }

            condition = true;
        }
            String name = firstName.concat(" ").concat(lastName);



            details.setEmail(emailAddress);
            details.setName(name);
            details.setPassword(confirmedPassword);

        try {
        makeServiceCall(this, getString(R.string.url_insert_personal_details),details);
            startActivity(new Intent(Registration.this, Login.class));

    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        return;
    }
        }

    public void makeServiceCall(Context context, String url, final Account details) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==0) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                  //  params.put("userID",details.getUserID());
                    params.put("name",details.getName());
                    params.put("email",details.getEmail());
                    params.put("password",details.getPassword());
                    return params;
                }


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}