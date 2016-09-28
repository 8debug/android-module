package gesoft.gcrashemail.bean;

/**
 * Created by yhr on 2016/9/28.
 * 邮件Bean
 */
public class GEmail {

    //邮箱账号  例如 xxx@163.com
    private String userName;
    //邮箱密码  例如 xxx@163.com
    private String userPwd;
    //发送邮件使用的昵称
    private String nick;
    //要发送到的地址
    private String toAddress;
    //邮件主题
    private String subject;

    public String getNick() {
        return nick;
    }

    /**
     * 发送邮件使用的昵称
     * @return
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * 邮件主题
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    /**
     * 要发送到的地址
     * @param toAddress
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * 邮箱账号  例如 xxx@163.com
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    /**
     * 邮箱密码  例如 xxx@163.com
     * @param userPwd
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }



}
