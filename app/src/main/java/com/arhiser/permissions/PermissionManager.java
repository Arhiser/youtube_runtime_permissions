package com.arhiser.permissions;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermissionManager {

    private Activity activity;

    private ArrayList<PermissionRequest> requests = new ArrayList<>();

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public PermissionRequest requestPermissionOrDo(String permission, Runnable doOnPermissionGranted) {
        PermissionRequest request = new PermissionRequest(requests.size(), permission, doOnPermissionGranted);
        requests.add(request);
        return request;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (PermissionRequest request: requests) {
            if (requestCode == request.getRequestId()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                request.getDoOnPermission().run();
            }
        }
    }

    public class PermissionRequest {
        private int requestId;
        private String permission;
        private Runnable doOnPermission;

        public PermissionRequest(int requestId, String permission, Runnable doOnPermission) {
            this.requestId = requestId;
            this.permission = permission;
            this.doOnPermission = doOnPermission;
        }

        public void perform() {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                doOnPermission.run();
            } else {
                ActivityCompat.requestPermissions(activity, new String[] { permission }, requestId);
            }
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public Runnable getDoOnPermission() {
            return doOnPermission;
        }

        public void setDoOnPermission(Runnable doOnPermission) {
            this.doOnPermission = doOnPermission;
        }
    }
}
