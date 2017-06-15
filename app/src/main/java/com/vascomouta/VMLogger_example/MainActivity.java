package com.vascomouta.VMLogger_example;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vascomouta.VMLogger.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    Log logger = new AppLogger().getLogger(MainActivity.class.getCanonicalName());
    Log logger2 = new AppLogger().getLogger("com.vascomouta.VMLogger_example.MainActivity.GrandChildren");

    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        findViewById(R.id.dump_log).setOnClickListener(this);
        findViewById(R.id.print_log).setOnClickListener(this);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.print_log:
                logger.verbose("print Console Message");
                ArrayList<User> users = new ArrayList<>();
                users.add(new User("Test", "value"));
                users.add(new User("Test2", "value1"));
                logger.verbose(users);
                /*logger.info();

                HashMap<String, User> map = new HashMap<>();
                map.put("item1", new User("Test", "value"));
                map.put("item2", new User("Test2", "value2"));
                logger.info(map);
                logger.info("Info message ");
                logger.debug("debug message");
                logger.warning("warning message");
                logger.error("Error message");
                logger.severe("severe message");
                logger.event(new User("testEvent", "test"));
                logger2.verbose("Grand Child relationship");

                AppLogger.printVerbose("Static call");*/

                break;
            case R.id.dump_log:
                AppLogger.dumpLog();
                break;
        }
    }
}
