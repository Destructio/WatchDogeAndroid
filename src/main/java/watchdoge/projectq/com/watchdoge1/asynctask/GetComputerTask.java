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

public class GetComputerTask extends AsyncTask<Void, Void, String> {
    private OnTaskComplete listener;
    private WeakReference<Context> weakContext;
    private ProgressDialog pDialog;

    public GetComputerTask(Context applicationContext, OnTaskComplete listener){
        weakContext = new WeakReference<>(applicationContext);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(weakContext.get());
        pDialog.setMessage("Обновление. Подождите...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        return getComputers();
    }

    @Override
    protected void onPostExecute(String jsonComputerList) {

        pDialog.dismiss();
        listener.onTaskCompletedComputer(jsonComputerList);
    }

    public String getComputers() {
        Context context = weakContext.get();

        String email = getEmail(context);

        String url = "http://" + context.getString(R.string.ipWEB) + "/getComputers?email=" + email;

        String jsonList = "";

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(url))
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            jsonList = response.body().string();

        } catch (IOException e) { e.printStackTrace(); }

        return jsonList;
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file), Context.MODE_PRIVATE);
        return sharedPref.getString("email", "None");
    }
}
