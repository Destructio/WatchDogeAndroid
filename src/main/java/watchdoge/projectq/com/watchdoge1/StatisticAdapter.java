package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import watchdoge.projectq.com.watchdoge1.archetype.ComputerStatistic;


public class StatisticAdapter extends ArrayAdapter<String> {
    //private Type type = new TypeToken<ArrayList<ComputerStatistic>>() {}.getType();

    public StatisticAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, R.layout.listview_statistic, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ComputerStatistic statistic = new Gson().fromJson(getItem(position), ComputerStatistic.class);
        String dateStr = statistic.getDate();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_statistic, parent, false);
        }

        TextView dateView = convertView.findViewById(R.id.statDate);

        dateView.setText(dateStr);

        return convertView;
    }
}
