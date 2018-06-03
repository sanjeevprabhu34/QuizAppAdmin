package apps.shankarson.com.quizappadmin.ui.StandaloneViews;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBar {
    public static ProgressBar instance;

    public  ProgressBar() {
        if (instance == null) {
            instance = new ProgressBar();
        } else {

        }
    }

    public static ProgressBar getInstance(){
        return instance;
    }

    public ProgressDialog createNewProgressDialog(Context callingContext) {
        ProgressDialog mProgressDialog =new ProgressDialog(callingContext);
        mProgressDialog.setMessage("Signing........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }



}
