package gesoft.push.po;

import java.io.Serializable;

/**
 * Created by yhr on 2016/7/1.
 * 封装XGPushRegisterResult
 */

public class GRMessage implements Serializable {

    private String account;
    private long accessId;
    private String deviceId;
    private String ticket;
    private short ticketType;
    private String token;

    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public short getTicketType() {
        return ticketType;
    }

    public void setTicketType(short ticketType) {
        this.ticketType = ticketType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
