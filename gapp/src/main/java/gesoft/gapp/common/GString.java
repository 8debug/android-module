package gesoft.gapp.common;

import android.text.TextUtils;

/**
 * Created by yhr on 2016/5/27.
 */
public class GString {

    public static Boolean isEmpty( String str ){
        return TextUtils.isEmpty(str);
    }

    public static Boolean isNotEmpty( String str ){
        return !TextUtils.isEmpty(str);
    }

    /**
     * 拼接字符串
     * @param obj 为 Object[] 或 Iterable
     * @param charSequence
     * @return
     */
    public static String join(Object obj, CharSequence charSequence){
        String str = null;
        if( obj instanceof Object[] ){
            str = TextUtils.join(charSequence, (Object[])obj);
        }else if( obj instanceof Iterable ){
            str = TextUtils.join( charSequence, (Iterable)obj );
        }
        return str;
    }
}
