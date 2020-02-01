package watchdoge.projectq.com.watchdoge1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import watchdoge.projectq.com.watchdoge1.archetype.Computer;

/**
 * Created by timurw10 on 17.03.2018.
 */

public class ComputerAdapter extends ArrayAdapter<String> {

    public ComputerAdapter(@NonNull Context context, @NonNull ArrayList<String> objects) {

        super(context, R.layout.activity_watch_doge, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Computer computer = new Gson().fromJson(getItem(position), Computer.class);
        String computerOS = computer.getOsName();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_computers, parent, false);
        }

        ImageView logo = convertView.findViewById(R.id.imageView);
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


        TextView name = convertView.findViewById(R.id.computerName);

        name.setText(computer.getComputerName());

        return convertView;
    }
}
