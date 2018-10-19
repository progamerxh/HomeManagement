package tkgd.homemanagement.Utility;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class DeviceSetting {
    static public String differentDensityAndScreenSize(Context context) {
        int value = 20;
        String str = "";
        String str_device = "";
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "small-ldpi";
                    // Log.e("small 1","small-ldpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    str = "small-mdpi";
                    // Log.e("small 1","small-mdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    str = "small-hdpi";
                    // Log.e("small 1","small-hdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    str = "small-xhdpi";
                    // Log.e("small 1","small-xhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    str = "small-xxhdpi";
                    // Log.e("small 1","small-xxhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    str = "small-xxxhdpi";
                    //Log.e("small 1","small-xxxhdpi");
                    value = 20;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    str = "small-tvdpi";
                    // Log.e("small 1","small-tvdpi");
                    value = 20;
                    break;
                default:
                    str = "small-unknown";
                    value = 20;
                    break;
            }

        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "normal-ldpi";
                    // Log.e("normal-ldpi 1","normal-ldpi");
                    str_device = "normal-ldpi";
                    value = 82;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    // Log.e("normal-mdpi 1","normal-mdpi");
                    str = "normal-mdpi";
                    value = 82;
                    str_device = "normal-mdpi";
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    // Log.e("normal-hdpi 1","normal-hdpi");
                    str = "normal-hdpi";
                    str_device = "normal-hdpi";
                    value = 82;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    //Log.e("normal-xhdpi 1","normal-xhdpi");
                    str = "normal-xhdpi";
                    str_device = "normal-xhdpi";
                    value = 90;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    // Log.e("normal-xxhdpi 1","normal-xxhdpi");
                    str = "normal-xxhdpi";
                    str_device = "normal-xxhdpi";
                    value = 96;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    //Log.e("normal-xxxhdpi","normal-xxxhdpi");
                    str = "normal-xxxhdpi";
                    str_device = "normal-xxxhdpi";
                    value = 96;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("DENSITY_TV 1","normal-mdpi");
                    str = "normal-tvdpi";
                    str_device = "normal-tvmdpi";
                    value = 96;
                    break;
                default:
                    // Log.e("normal-unknown","normal-unknown");
                    str = "normal-unknown";
                    str_device = "normal-unknown";
                    value = 82;
                    break;
            }
        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    str = "large-ldpi";
                    // Log.e("large-ldpi 1","normal-ldpi");
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    str = "large-mdpi";
                    //Log.e("large-ldpi 1","normal-mdpi");
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    //Log.e("large-ldpi 1","normal-hdpi");
                    str = "large-hdpi";
                    value = 78;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    // Log.e("large-ldpi 1","normal-xhdpi");
                    str = "large-xhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    //Log.e("large-ldpi 1","normal-xxhdpi");
                    str = "large-xxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    // Log.e("large-ldpi 1","normal-xxxhdpi");
                    str = "large-xxxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("large-ldpi 1","normal-tvdpi");
                    str = "large-tvdpi";
                    value = 125;
                    break;
                default:
                    str = "large-unknown";
                    value = 78;
                    break;
            }

        } else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            switch (context.getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_LOW:
                    // Log.e("large-ldpi 1","normal-ldpi");
                    str = "xlarge-ldpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    // Log.e("large-ldpi 1","normal-mdpi");
                    str = "xlarge-mdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    //Log.e("large-ldpi 1","normal-hdpi");
                    str = "xlarge-hdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    // Log.e("large-ldpi 1","normal-hdpi");
                    str = "xlarge-xhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    // Log.e("large-ldpi 1","normal-xxhdpi");
                    str = "xlarge-xxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    // Log.e("large-ldpi 1","normal-xxxhdpi");
                    str = "xlarge-xxxhdpi";
                    value = 125;
                    break;
                case DisplayMetrics.DENSITY_TV:
                    //Log.e("large-ldpi 1","normal-tvdpi");
                    str = "xlarge-tvdpi";
                    value = 125;
                    break;
                default:
                    str = "xlarge-unknown";
                    value = 125;
                    break;
            }
        }

        return str_device;
    }
}
