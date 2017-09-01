package gv.anathan.meutreino;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import gv.anathan.meutreino.Classes.Utility;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;
    private TextView tvLoginSigup;
    private Button btLogInSignUp;
    private Snackbar mySnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserPassword = (EditText) findViewById(R.id.etUserPassword);
        etUserConfirmPassword = (EditText) findViewById(R.id.etUserConfirmPassword);
        tvLoginSigup = (TextView) findViewById(R.id.tvLogInSigUp);
        btLogInSignUp = (Button) findViewById(R.id.btSignUpLogIn);

        //checks if there is internet connection
        if (!Utility.isNetworkAvailable(this)){

            showNetworkConnectionMessage();

        }else {

            //checks if user has logged in
            if (ParseUser.getCurrentUser() != null) {
                authorizedAccess();
            }
        }
    }

    //shows internet connection message
    public void showNetworkConnectionMessage(){

        mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Sem conexao de internet", Snackbar.LENGTH_INDEFINITE);
        mySnackbar.setAction("Reconectar", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Utility.isNetworkAvailable(getApplicationContext())){

                    mySnackbar.dismiss();

                }else{
                    mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Sem conexao de internet", Snackbar.LENGTH_LONG).setAction("",null);
                    mySnackbar.show();

                }
            }
        });
        mySnackbar.show();
    }


    //alternates layout between log in and sign up functionality
    public void alternateSignUpLogIn(View view){

        if (tvLoginSigup.getText().toString().equals("Sign up")) {

            etUserConfirmPassword.setVisibility(View.VISIBLE);
            tvLoginSigup.setText("Log in");
            btLogInSignUp.setText("Sign up");

        }else{

            etUserConfirmPassword.setVisibility(View.INVISIBLE);
            tvLoginSigup.setText("Sign up");
            btLogInSignUp.setText("Log in");

        }
    }


    //validates fields
    public boolean verifyFields(View view){

        if (etUserName.getText().toString().isEmpty()) {

            //Snackbar.make(view, "Necessario Informar Nome", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            etUserName.setError("Necessario informar nome");
            return false;

        }
        if (etUserPassword.getText().toString().isEmpty()){

            //Snackbar.make(view, "Necessario Informar Password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            etUserPassword.setError("Necessario Informar Password");
            return false;

        }
        if ( btLogInSignUp.getText().toString().equals("Sign up")){

            if (etUserConfirmPassword.getText().toString().isEmpty()) {

                //Snackbar.make(view, "Necessario Confirmar Password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                etUserConfirmPassword.setError("Necessario Confirmar Password");
                return false;

            }else{
                if (!etUserPassword.getText().toString().equals(etUserConfirmPassword.getText().toString())) {

                    //Snackbar.make(view, "Password e confirmacao sao diferentes", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    etUserConfirmPassword.setError("Password e confirmacao sao diferentes");
                    return false;

                }
            }
        }

        return true;
    }


    //sign up or log in user
    public void signUpLogIn(View view){

        if (!Utility.isNetworkAvailable(this)){

            showNetworkConnectionMessage();

        }else {

            if (verifyFields(view)) {

                //gets user information
                ParseUser user = new ParseUser();
                user.setUsername(String.valueOf(etUserName.getText()));
                user.setPassword(String.valueOf(etUserPassword.getText()));

                //log in
                if (btLogInSignUp.getText().toString().equals("Log in")) {

                    Utility.showLoading(this);

                    //log in user
                    user.logInInBackground(etUserName.getText().toString(), etUserPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e == null) {
                                authorizedAccess();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Invalido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    Utility.dismissLoading();

                    //sign in
                } else {

                    //register new user
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                authorizedAccess();
                            } else {
                                Toast.makeText(getApplicationContext(), "Sign up error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }


    public void authorizedAccess(){

        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
}
