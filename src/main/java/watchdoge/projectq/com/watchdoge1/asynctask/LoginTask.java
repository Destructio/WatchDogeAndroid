package watchdoge.projectq.com.watchdoge1.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import watchdoge.projectq.com.watchdoge1.R;

public class LoginTask extends AsyncTask<String,Void,String>
{
    private ProgressDialog pDialog;
    private OnTaskComplete listener;
    private WeakReference<Context> weakContext;


    public LoginTask(Context context,OnTaskComplete listener) {
        this.weakContext = new WeakReference<>(context);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(weakContext.get());
        pDialog.setMessage("Авторизация. Подождите...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace();}

        String isSuccess;

        String userEmail =  strings[0];
        String userPass = strings[1];

        String url = "http://" + weakContext.get().getString(R.string.ipWEB) + "/login?email=" + userEmail  + "&pass=" + userPass;

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(url))
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            isSuccess = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = "-1";
        }

        return isSuccess;
    }

    @Override
    protected void onPostExecute(String result) {
        pDialog.dismiss();
        listener.onTaskCompletedComputer(result);
    }

}
