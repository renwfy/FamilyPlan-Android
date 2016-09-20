package com.familyplan.ihealth.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionManager {
    Context context;
    static  boolean VersionChecked = false;

//    public void checkVersion(final Context context) {
//        this.context = context;
//        Api.appupgrade(new NSCallback<AppModel>(context, AppModel.class) {
//            @Override
//            public void onSuccess(final AppModel model) {
//                if (model.getIs_upgrade() == 0) {
//                    return;
//                }
//                VersionChecked = true;
//                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                dialog.setTitle("提示");
//                dialog.setMessage("App有更新，是否去更新？");
//                dialog.setPositiveButton("更新", new AlertDialog.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String url = model.getDownload_url(); // web address
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        context.startActivity(intent);
//                    }
//                });
//                dialog.setCancelable(false);
//                dialog.show();
//            }
//        });
//    }


    /**
     * @return 客户端应用程序的版本号
     */
    public static int getAppVersion(Context context) {
        int version = 0;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getAppVersionName(Context context) {
        String version = "";
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
