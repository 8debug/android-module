package gesoft.push;

/**
 * Created by Administrator on 2016/6/14.
 */
public class GPushConstant {

    //接收消息透传
    public static final String XG_ACTION_PUSH_MESSAGE  = "com.tencent.android.tpush.action.PUSH_MESSAGE";
    //监听注册、反注册、设置/删除标签、通知被点击等处理结果
    public static final String XG_ACTION_FEEDBACK  = "com.tencent.android.tpush.action.FEEDBACK";

    public static final String XG_NOTIFACTION_SHOW_RECEIVER = "NotifactionShowReceive";

    public static final String XG_NOTIFACTION_CLICK_RECEIVER = "NotifactionClickReceive";

    //点击通知时此作为Bundle参数传入
    public static final String XG_INTENT_BUNDLE = GPushConstant.class.getClass().getName();
    public static final String XG_BUNDLE_GMESSAGE = "gmessage";
}
