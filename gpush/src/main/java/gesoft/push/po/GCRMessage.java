package gesoft.push.po;

import java.io.Serializable;

/**
 * Created by yhr on 2016/7/1.
 * 封装XGPushClickedResult
 */

public class GCRMessage implements Serializable {

    private String title;
    private String content;
    private long actionType;
    private String customContent;
    private long msgId;
    private int notificationActionType;
    private String activityName;

    public int getNotificationActionType() {
        return notificationActionType;
    }

    public void setNotificationActionType(int notificationActionType) {
        this.notificationActionType = notificationActionType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getActionType() {
        return actionType;
    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
