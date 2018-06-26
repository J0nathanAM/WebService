package jonathanalvarez.webservice;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class MyPreference  {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    private static final int PRIVATE_MODE=0;
    public static final String PREFER_NAME="RestaurantApp";
    public static final String IN_FIRST_TIME="InFirtsTime";
    public static final String USER_NAME="name";

    public MyPreference(Context context){
        this.context=context;
        preferences=context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public boolean isFirstTime(){
        return preferences.getBoolean(IN_FIRST_TIME,true);
    }

    public void setOld( boolean b){
        if(b){
            editor.putBoolean(IN_FIRST_TIME,false);
            editor.commit();
        }
    }

    public String getUserName(){
        return preferences.getString(USER_NAME,"");
    }

    public void setUserName(String name){
        editor.putString(USER_NAME,name);
        editor.commit();
    }
}
