package com.arhiser.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager.PermissionRequest callRequest = permissionManager.requestPermissionOrDo(
                Manifest.permission.CALL_PHONE, this::makeCall);

        findViewById(R.id.button_call).setOnClickListener(v -> {
            callRequest.perform();
        });
    }

    private void makeCall() {
        String uri = "tel:00000000000000";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

}
