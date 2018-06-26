package jonathanalvarez.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class ConexionDetector {
    private Context context;
    ConexionDetector(Context c){
        context=c;
    }

    public boolean isConnectedInternet(){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=manager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }
}
