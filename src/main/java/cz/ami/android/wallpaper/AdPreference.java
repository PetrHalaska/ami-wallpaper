package cz.ami.android.wallpaper;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdPreference extends Preference {
    public AdPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AdPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdPreference(Context context) {
        super(context);
    }

    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        Activity activity = (Activity) getContext();
        AdView adView = new AdView(activity, AdSize.BANNER, activity.getString(R.string.admobid));
        ((LinearLayout) view).addView(adView);
        adView.loadAd(new AdRequest());
        return view;
    }
}
