package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by glarencezhao on 10/27/16.
 */

public class Debug {

    public Debug(){}

    public static void printToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
