import java.text.SimpleDateFormat;

/**
 * Created by yuhongwen
 * on 2020/11/20
 */
public class DateFormatUtils {
    public static String formatDate(Long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS") .format(time);
    }
}
