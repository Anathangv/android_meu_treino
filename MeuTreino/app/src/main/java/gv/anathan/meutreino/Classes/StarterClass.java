package gv.anathan.meutreino.Classes;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

public class StarterClass extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //connect and initialize parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tj6cdhec7so6p3ULBb52UCq66HqyDGlbgPMVXtGA")
                .clientKey("e4A0pC1ewrGyFfVBDOkjezbEZhMB9YotQFE0z2CA")
                .server("https://parseapi.back4app.com/").build());

        Log.i("AppInfo","connected");
    }

}
