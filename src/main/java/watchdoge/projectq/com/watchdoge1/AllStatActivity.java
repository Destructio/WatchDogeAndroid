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
import watchdoge.projectq.com.watchdoge1.archetype.ComputerStatistic;
import watchdoge.projectq.com.watchdoge1.asynctask.GetAllStatTask;
import watchdoge.projectq.com.watchdoge1.asynctask.OnTaskComplete;

public class AllStatActivity extends AppCompatActivity implements OnTaskComplete {

    private Type itemsListType = new TypeToken<ArrayList<String>>() {
    }.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stat);

        final Computer computer = new Gson().fromJson(getIntent().getStringExtra("computer"), Computer.class);

        FloatingActionButton fab = findViewById(R.id.fab4);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(computer);
            }
        });

        update(computer);
    }

    private void update(Computer computer) {
        GetAllStatTask task = new GetAllStatTask(AllStatActivity.this, this);

        task.execute(computer.getComputerName());
    }


    @Override
    public void onTaskCompletedComputer(ArrayList<String> arrString) {
    }

    @Override
    public void onTaskCompletedComputer(String json) {
        final ArrayList<String> statArr = new Gson().fromJson(json, itemsListType);

        ListView listView = findViewById(R.id.listView2);

        StatisticAdapter adapter = new StatisticAdapter(AllStatActivity.this, statArr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ComputerStatistic statistic = new Gson().fromJson(statArr.get(position), ComputerStatistic.class);
                startActivity(new Intent(AllStatActivity.this, ShowStatActivity.class).putExtra("stat", new Gson().toJson(statistic)));
            }
        });
    }

}
