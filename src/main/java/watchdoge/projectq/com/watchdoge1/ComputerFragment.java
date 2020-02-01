package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import watchdoge.projectq.com.watchdoge1.archetype.Computer;
import watchdoge.projectq.com.watchdoge1.archetype.ComputerStatistic;
import watchdoge.projectq.com.watchdoge1.archetype.MyProcess;
import watchdoge.projectq.com.watchdoge1.asynctask.GetStatisticTask;
import watchdoge.projectq.com.watchdoge1.asynctask.OnTaskComplete;

public class ComputerFragment extends Fragment {

    private Gson gson = new Gson();

    public Integer position;
    public View fragmentView;
    public Computer computer;

    public Type computerType = new TypeToken<Computer>() {
    }.getType();
    public Type statisticType = new TypeToken<ComputerStatistic>() {
    }.getType();

    static ComputerFragment newInstance(int position, String computerJSON) {
        ComputerFragment f = new ComputerFragment();

        Bundle args = new Bundle();
        args.putString("json", computerJSON);
        args.putInt("pos", position);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        computer = gson.fromJson(getArguments().getString("json"), computerType);

        position = getArguments().getInt("pos");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        switch (position) {

            case 1:
                fragmentView = baseComputerInfo(inflater, container);
                break;

            case 0:
                fragmentView = detailComputerInfo(inflater, container);
                break;
        }
        return fragmentView;
    }

    private View detailComputerInfo(LayoutInflater inflater, ViewGroup container) {

        View fragmentViewL = inflater.inflate(R.layout.fragment_computer_info_l, container, false);
        final Context context = inflater.getContext();
        final TextView text = fragmentViewL.findViewById(R.id.fragmentComputerInfoL_TextView);
        final FloatingActionButton refreshFab = fragmentViewL.findViewById(R.id.fab2);
        final FloatingActionButton shareFab = fragmentViewL.findViewById(R.id.fab3);
        final Button button = fragmentViewL.findViewById(R.id.btn_allStat);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab2:
                        refreshStat(computer, text);
                        break;
                    case R.id.fab3:
                        shareStat();
                        break;
                    case R.id.btn_allStat:
                        System.out.println("HERE");
                        showAllStat(context);
                        break;

                }

            }
        };

        refreshStat(computer, text);

        refreshFab.setOnClickListener(listener);
        shareFab.setOnClickListener(listener);
        button.setOnClickListener(listener);


        return fragmentViewL;
    }

    private void showAllStat(Context context) {

        startActivity(new Intent(context, AllStatActivity.class).putExtra("computer", new Gson().toJson(computer)));
    }

    private void shareStat() {
        GetStatisticTask task = new GetStatisticTask(getContext(), new OnTaskComplete() {
            @Override
            public void onTaskCompletedComputer(ArrayList<String> arrString) {
            }

            @Override
            public void onTaskCompletedComputer(String str) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/*");
                startActivity(sendIntent);
            }
        });
        task.execute(computer);
    }

    private void refreshStat(Computer computer, final TextView text) {
        GetStatisticTask task = new GetStatisticTask(getContext(), new OnTaskComplete() {
            @Override
            public void onTaskCompletedComputer(ArrayList<String> arrString) {
            }

            @Override
            public void onTaskCompletedComputer(String str) {
                ComputerStatistic statistic = gson.fromJson(str, statisticType);
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
        });
        task.execute(computer);
    }

    private View baseComputerInfo(LayoutInflater inflater, ViewGroup container) {
        View fragmentViewB = inflater.inflate(R.layout.fragment_computer_info_basic, container, false);

        TextView textViewName = fragmentViewB.findViewById(R.id.fragmentComputerInfoTextName);
        TextView textViewInfo = fragmentViewB.findViewById(R.id.fragmentComputerInfoTextBase);
        ImageView logo = fragmentViewB.findViewById(R.id.fragmentComputerInfoImage);
        FloatingActionButton fab = fragmentViewB.findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, new Gson().toJson(computer));
                sendIntent.setType("text/*");
                startActivity(sendIntent);
            }
        });

        List<String> disks = computer.getDiskList();
        String computerOS = computer.getOsName();
        ArrayList<String> disksNames = new ArrayList<>();
        for (int i = 0; i < disks.size(); i++) {
            String str = disks.get(i);
            disksNames.add(str.substring(0, str.length() - 33));
        }

        logo.setImageResource(R.drawable.default_logo);

        switch (computerOS) {
            case "Windows":
                logo.setImageResource(R.drawable.win_logo);
                break;
            case "Ubuntu":
                logo.setImageResource(R.drawable.linux_logo);
                break;
            case "MacOS":
                logo.setImageResource(R.drawable.macos_logo);
                break;
            case "FreeBSD":
                logo.setImageResource(R.drawable.freebsd_logo);
            case "Solaris":
                logo.setImageResource(R.drawable.solaris_logo);
        }

        textViewName.setText(computer.getComputerName());

        textViewInfo.setText(
                "OS: " + computer.getOsName() + " " + computer.getOsVersion() +
                        "\nIPv4: " + computer.getComputerIPv4() +
                        "\nIPv6: " + computer.getComputerIPv6() +
                        "\nTotal RAM: " + computer.getTotalRAM() +
                        "\nCPU: " + computer.getCpu() +
                        "\nCores/Threads: " + computer.getCpuCores() + "/" + computer.getCpuLogicalCores() +
                        "\nMAC: " + computer.getComputerMac() +
                        "\nDisks: " + Arrays.toString(disksNames.toArray())
        );

        return fragmentViewB;
    }

}

