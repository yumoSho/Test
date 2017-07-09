package com.glanway.jty.service.sms;

/**
 */
public class AccessToken {
    private String id;
    private String clientId;
    private String accessCode;

    public AccessToken() {
    }

    public AccessToken(String id, String clientId, String accessCode) {
        this.id = id;
        this.clientId = clientId;
        this.accessCode = accessCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
