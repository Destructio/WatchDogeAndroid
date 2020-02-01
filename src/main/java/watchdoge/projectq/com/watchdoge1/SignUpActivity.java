package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import watchdoge.projectq.com.watchdoge1.asynctask.OnTaskComplete;
import watchdoge.projectq.com.watchdoge1.asynctask.SignUpTask;


public class SignUpActivity extends AppCompatActivity implements OnTaskComplete {


    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void onClickSignUpActivity(View view) {
        switch (view.getId()) {

            case R.id.textView_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_signup:
                System.out.println(R.id.btn_signup);
                signUp(view.getContext());
                break;

        }
    }

    private void signUp(Context context) {
        editTextName = findViewById(R.id.input_name);
        editTextEmail = findViewById(R.id.input_email);
        editTextPass = findViewById(R.id.input_password);

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String pass = editTextPass.getText().toString();


        if (name.length() > 5 && isValidEmail(email) && pass.length() > 6) {
            SignUpTask st = new SignUpTask(SignUpActivity.this, this);
            st.execute(name, email, pass, context.toString());

        } else {
            editTextName.setError("Длинна имени должно быть > 5!");
            editTextEmail.setError("Email не валиден!");
            editTextPass.setError("Длинна пароля должна быть > 6! ");
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onTaskCompletedComputer(ArrayList<String> arrString) {
    }

    @Override
    public void onTaskCompletedComputer(String str) {
        if (str.equals("1")) {
            startActivity(new Intent(SignUpActivity.this, WatchDogeActivity.class));
        } else if (str.equals("-1")) {
            String err = "Что то пошло не так! Попробуйте перезапустить приложение";
            editTextName.setError(err);
            editTextEmail.setError(err);
            editTextPass.setError(err);

        }
    }
}
