package com.vascomouta.VMLogger_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppLogger logger = new AppLogger().getLogger(MainActivity.class.getCanonicalName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.print_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.verbose("print Console Message");
            }
        });

    }
}
