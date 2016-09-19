package gesoft.gapp.common;

/**
 * Created by yhr on 2016/9/19.
 *
 */
public class GNumber {

    /**
     * 验证是否为纯数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
