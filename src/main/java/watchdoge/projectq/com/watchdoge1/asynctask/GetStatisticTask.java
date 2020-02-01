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
import watchdoge.projectq.com.watchdoge1.archetype.Computer;

public class GetStatisticTask extends AsyncTask<Computer, Void, String> {

    private OnTaskComplete listener;
    private WeakReference<Context> weakContext;
    private ProgressDialog pDialog;

    public GetStatisticTask(Context context, OnTaskComplete listener) {
        weakContext = new WeakReference<>(context);
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
    protected String doInBackground(Computer... computer) {

        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        Computer computer1 = computer[0];

        String email = getEmail(weakContext.get());

        String url = "http://" + weakContext.get().getString(R.string.ipWEB) + "/getJSON?computer=" + computer1.getComputerName() + "&email=" + email;

        System.out.println("URL = " + url);
        Response response;
        String statisticJSON = "";

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(url))
                .build();
        try {
            response = httpClient.newCall(request).execute();
            statisticJSON = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (statisticJSON.equals("-1")) {
            System.err.print("SQL EXCEPTION!");
        } else if (statisticJSON.equals("NULL")) {
            System.err.println("NONE STATISTIC");
        }
        return statisticJSON;
    }


    protected void onPostExecute(String statisticJSON) {
        listener.onTaskCompletedComputer(statisticJSON);
        pDialog.dismiss();
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.file), Context.MODE_PRIVATE);
        return sharedPref.getString("email", "None");
    }
}
