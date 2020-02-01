package watchdoge.projectq.com.watchdoge1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import watchdoge.projectq.com.watchdoge1.archetype.Computer;
import watchdoge.projectq.com.watchdoge1.asynctask.GetComputerTask;
import watchdoge.projectq.com.watchdoge1.asynctask.OnTaskComplete;

public class WatchDogeActivity extends AppCompatActivity implements OnTaskComplete {

    private Type itemsListType = new TypeToken<ArrayList<String>>() {
    }.getType();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_doge);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        update();

    }

    @Override
    public void onTaskCompletedComputer(final ArrayList<String> arrString) {
    }

    @Override
    public void onTaskCompletedComputer(String str) {
        final ArrayList<String> computerArrayList = new Gson().fromJson(str, itemsListType);

        ListView listView = findViewById(R.id.listView);

        ComputerAdapter adapter = new ComputerAdapter(WatchDogeActivity.this, computerArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Computer computer = new Gson().fromJson(computerArrayList.get(position), Computer.class);
                startActivity(new Intent(WatchDogeActivity.this, ComputerInfoActivity.class).putExtra("computer", new Gson().toJson(computer)));
            }
        });
    }


    private void update() {

        GetComputerTask getComputersTask = new GetComputerTask(WatchDogeActivity.this, this);
        getComputersTask.execute();
    }

}
