package watchdoge.projectq.com.watchdoge1;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class ComputerInfoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_info);

        String json = getIntent().getStringExtra("computer");

        update(json);
    }


    public void update(String json) {
        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), json);
        pager.setAdapter(pagerAdapter);
    }
}
