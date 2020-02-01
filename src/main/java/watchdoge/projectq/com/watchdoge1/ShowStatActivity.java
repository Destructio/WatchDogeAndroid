package watchdoge.projectq.com.watchdoge1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import watchdoge.projectq.com.watchdoge1.archetype.ComputerStatistic;
import watchdoge.projectq.com.watchdoge1.archetype.MyProcess;

public class ShowStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stat);
        TextView text = findViewById(R.id.statView);
        String json = getIntent().getStringExtra("stat");
        ComputerStatistic statistic = new Gson().fromJson(json, ComputerStatistic.class);
        MyProcess process0 = statistic.getProcessList().get(0);
        MyProcess process1 = statistic.getProcessList().get(1);
        MyProcess process2 = statistic.getProcessList().get(2);
        MyProcess process3 = statistic.getProcessList().get(3);
        MyProcess process4 = statistic.getProcessList().get(4);
        text.setText(
                "CPU Температура: " + statistic.getCpuTemp()
                        + "\nRAM Использование: " + statistic.getRamLoad()
                        + "\nCPU Использование: " + statistic.getCpuLoad() + "%"
                        + "\nHDD Загруженность: " + statistic.getHddLoad()
                        + "\nUPTIME: " + statistic.getUpTime()
                        + "\nНаивысшая загруска CPU: " + statistic.getProcessList().get(1).getName() + " " + statistic.getProcessList().get(1).getCpuload() + "%"
                        + "\nПоследняя отправка статистики: " + statistic.getDate()
                        + "\n\nСписок процессов:"
                        + "\nPID: Имя: CPU: RAM:"
                        + "\n1) " + process0.getPID() + " " + process0.getName() + " " + process0.getCpuload() + "% " + process0.getRSS()
                        + "\n2) " + process1.getPID() + " " + process1.getName() + " " + process1.getCpuload() + "% " + process1.getRSS()
                        + "\n3) " + process2.getPID() + " " + process2.getName() + " " + process2.getCpuload() + "% " + process2.getRSS()
                        + "\n4) " + process3.getPID() + " " + process3.getName() + " " + process3.getCpuload() + "% " + process3.getRSS()
                        + "\n5) " + process4.getPID() + " " + process4.getName() + " " + process4.getCpuload() + "% " + process4.getRSS()
        );
    }
}
