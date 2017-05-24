package com.vascomouta.VMLogger_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vascomouta.VMLogger.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Log logger = new AppLogger(MainActivity.class.getCanonicalName()).getLogger();

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
                HashMap<String, User> map = new HashMap<>();
                map.put("item1", new User("Test", "value"));
                map.put("item2", new User("Test2", "value2"));
                logger.info(map);
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
