package app.com.example.android.RealmTest;

/**
 * Created by ohthe on 5/27/2016.
 */
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;
import android.app.Activity;


public class InputFilterMinMax implements InputFilter {

    private int min, max;
    Context c;

    public InputFilterMinMax(Context context, int min, int max) {
        this.min = min;
        this.max = max;
        c = context;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
            // Add the new string in
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());
            int input = Integer.parseInt(newVal);
            if (isInRange(min, max, input))
                return null;
            } catch (NumberFormatException nfe) {
            }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}