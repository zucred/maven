package com.vascomouta.VMLogger_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppLogger logger = VMLoggerApplication.getInstance().applogger.getLogger(MainActivity.class.getCanonicalName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.print_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.verbose("print Console Message");
                logger.info();
                ArrayList<User> users = new ArrayList<>();
                users.add(new User("Test", "value"));
                users.add(new User("Test2", "value1"));
                logger.info(users);
             //   logger.info("Info message ");
               // logger.debug("debug message");
               // logger.warning("warning message");
               // logger.error("Error message");
               // logger.severe("severe message");
              //  logger.event();
            }
        });

    }
}
