package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import watchdoge.projectq.com.watchdoge1.asynctask.LoginTask;
import watchdoge.projectq.com.watchdoge1.asynctask.OnTaskComplete;

public class LoginActivity extends AppCompatActivity implements OnTaskComplete {

    private EditText emailEditText;
    private EditText passEditText;
    private String email;
    private String password;

    public static void saveAcc(Context context, String name, String email, String passS) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", passS);
        editor.apply();
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = getIntent().getStringExtra("email");
        //getActionBar().hide();
        emailEditText = findViewById(R.id.editText_email_login);
        passEditText = findViewById(R.id.editText_pass_login);
        emailEditText.setText(email);
    }

    public void onClickLoginActivity(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                login();
                break;
            case R.id.textView_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }

    private void login() {

        email = emailEditText.getText().toString();
        password = passEditText.getText().toString();

        if (email.length() == 0 || password.length() == 0) {
            emailEditText.setError("NULL");
            passEditText.setError("NULL");
        } else {
            LoginTask task = new LoginTask(LoginActivity.this, this);
            task.execute(email, password);
        }
    }

    @Override
    public void onTaskCompletedComputer(ArrayList<String> arrString) {
    }

    @Override
    public void onTaskCompletedComputer(String str) {
        Context context = LoginActivity.this;
        if (str.equals("true")) {
            saveAcc(context, "", email, password);
            startActivity(new Intent(context, WatchDogeActivity.class).putExtra("email", email).putExtra("password", password));
        } else {
            emailEditText.setError("Проверьте правильность данных!");
            passEditText.setError("Проверьте правильность данных!");
        }
    }
}
