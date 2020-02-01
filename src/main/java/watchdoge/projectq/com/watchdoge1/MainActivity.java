package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isAlreadyHaveAcc(this)) {
            startActivity(new Intent(this, LoginActivity.class).putExtra("email", email));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public boolean isAlreadyHaveAcc(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file), Context.MODE_PRIVATE);
        email = sharedPref.getString("email", null);
        String password = sharedPref.getString("password", null);
        return email != null && password != null;
    }


}
