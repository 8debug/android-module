package gesoft.push.po;

import java.io.Serializable;

/**
 * Created by yhr on 2016/7/1.
 * 封装XGPushTextMessage
 */

public class GTextMessage implements Serializable {

    private String title;
    private String content;
    private String customContent;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
