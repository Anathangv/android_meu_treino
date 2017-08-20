package gv.anathan.meutreino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etUserPassword;
    private EditText etUserConfirmPassword;
    private TextView tvLoginSigup;
    private Button btLogInSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserPassword = (EditText) findViewById(R.id.etUserPassword);
        etUserConfirmPassword = (EditText) findViewById(R.id.etUserConfirmPassword);
        tvLoginSigup = (TextView) findViewById(R.id.tvLogInSigUp);
        btLogInSignUp = (Button) findViewById(R.id.btSignUpLogIn);


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
    public boolean verifyFields(){

        if (etUserName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Necessario Informar Nome", Toast.LENGTH_SHORT).show();
            return false;

        }else if (etUserPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Necessario Informar Password", Toast.LENGTH_SHORT).show();
            return false;

        }else if ( btLogInSignUp.getText().toString().equals("Sign up") && etUserConfirmPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Necessario Confirmar Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void signUpLogIn(View view){

        if (verifyFields()){

            if (btLogInSignUp.getText().toString().equals("Log in")) {

                Toast.makeText(getApplicationContext(), "Checar usuario e fazer loggin", Toast.LENGTH_SHORT).show();

            }else{

                if (etUserPassword.getText().toString().equals(etUserConfirmPassword.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Gravar usuario", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getApplicationContext(), "Confirmacao de password nao corresponde ao informado", Toast.LENGTH_SHORT).show();

                }
            }
        }



    }
}
