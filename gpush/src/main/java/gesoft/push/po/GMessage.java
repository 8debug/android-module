package gesoft.push.po;

/**
 * Created by Administrator on 2016/7/1.
 */

public class GMessage {

    private String title;
    private String content;
    private long actionType;
    private String customContent;
    private long msgId;

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
