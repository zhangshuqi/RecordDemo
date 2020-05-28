package com.example.recorddemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.recorddemo.R;
import com.example.recorddemo.permissions.PermissionListener;
import com.example.recorddemo.permissions.PermissionsUtil;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "tank";
    public String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionsUtil.requestPermission(this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        RecodeManager.getInstance().getRecodeFileList("");
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        //  ToastUtils.showShort("请到设置-权限管理中开启");
                    }
                }, permissions, true, null
        );

        ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.CallFeaturesSetting");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        String emui = getEMUI();
        String emotionUI = emui.replace("EmotionUI_", "");
        Log.d(TAG, emotionUI);
        Log.d(TAG, queryRecordSetting());
        Toast.makeText(this, emui, Toast.LENGTH_SHORT).show();
    }

    public static String getEMUI() {
        Class<?> classType = null;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildVersion;
    }
    private String queryRecordSetting() {//查询value值
        try {
            Uri uri = Uri.parse("content://settings/system/button_auto_record_call");
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
