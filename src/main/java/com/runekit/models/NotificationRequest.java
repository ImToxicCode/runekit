package com.runekit.models;

public class NotificationRequest {
    public String RsName;
    public String Type;
    public String Name;
    public int Minutes;

    public String getRsName() {
        return RsName;
    }

    public void setRsName(String rsName) {
        RsName = rsName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int minutes) {
        Minutes = minutes;
    }
}
