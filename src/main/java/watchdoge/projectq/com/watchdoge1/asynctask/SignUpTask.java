package watchdoge.projectq.com.watchdoge1.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import watchdoge.projectq.com.watchdoge1.R;

public class SignUpTask extends AsyncTask<String, Void, String> {

    private ProgressDialog pDialog;
    private WeakReference<Context> weakContext;
    private OnTaskComplete listener;

    private Response response;

    public SignUpTask(Context context,OnTaskComplete listener) {
        this.weakContext = new WeakReference<>(context);
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(weakContext.get());
        pDialog.setMessage("Загрузка. Подождите...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        String resultCode;

        String name = strings[0];
        String email = strings[1];
        String password = strings[2];

        String url = "http://" + weakContext.get().getString(R.string.ipWEB) + "/reg?name=" + name + "&email=" + email + "&pass=" + password;

        System.out.println("Sign Up to : " + url + " \n with [" + name + "] [" + email + "] [" + password +"]");

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(url))
                .build();

        try {
            response = httpClient.newCall(request).execute();
            resultCode = response.body().string(); // 1 or 0 or -1(if sql exception) 404 error can be
        } catch (IOException e) { e.printStackTrace(); resultCode = "-1";}

        if (resultCode.equals("1"))
            saveAcc(weakContext.get(), name, email, password);

        return resultCode;
    }

    @Override
    protected void onPostExecute(String resultCode) {
        super.onPostExecute(resultCode);
        listener.onTaskCompletedComputer(resultCode);
        pDialog.dismiss();
    }

    public static void saveAcc(Context context, String name, String email, String passS)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("password",passS);
        editor.apply();
        editor.commit();
    }
}
