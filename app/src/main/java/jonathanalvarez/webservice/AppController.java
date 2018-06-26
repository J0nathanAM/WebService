package jonathanalvarez.webservice;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class AppController extends Application {

    private static final String TAG="AppController";
    private static AppController instance;

    public static synchronized AppController getInstance(){
        return instance;
    }

    private RequestQueue requestQueue;

    public void onCreate(){
        super.onCreate();
        instance=this;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addtoRecuestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public <T> void addtoRecuestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }

    public void cancelPenddingRequest(Object tag){
        if (requestQueue!=null){
            requestQueue.cancelAll(tag);
        }
    }
}
