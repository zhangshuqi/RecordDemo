package com.example.recorddemo;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class RecodeManager {
    private static class RecodeManagerSingleton {
        private static final RecodeManager INSTANCE = new RecodeManager();

    }

    public static RecodeManager getInstance() {
        return RecodeManager.RecodeManagerSingleton.INSTANCE;
    }

    private RecodeManager() {

    }

    public void getRecodeFileList(String path) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String paths = "/storage/emulated/0/Sounds/CallRecord";
        Log.d("tank",paths);
        Log.d("tank",externalStorageDirectory.getAbsolutePath()+"/Sounds/CallRecord");
        File file = new File(paths);
        Log.d("tank",externalStorageDirectory.getAbsolutePath());
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                Log.d("tank", file1.getName());
            }
        }
    }
}
