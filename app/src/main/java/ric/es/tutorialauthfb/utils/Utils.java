package ric.es.tutorialauthfb.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ricardo on 23/10/2016.
 */

public class Utils {

    /**
     * Usage from activity:
     * View view = getCurrentFocus();
     * hideKeyboard(view,this);
     *
     * @param view
     */
    public static void hideKeyboard(View view, Context context) {

        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
