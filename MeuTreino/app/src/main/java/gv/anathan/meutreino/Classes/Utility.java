package gv.anathan.meutreino.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

    private static ProgressDialog progressDialog;

    public static boolean isNetworkAvailable(Context context){


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        boolean statusConnection = netInfo != null && netInfo.isConnected();

        return statusConnection;

    }

    public static void showLoading(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public static void dismissLoading(){
        progressDialog.dismiss();
    }

}
