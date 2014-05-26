package com.huayuan.domain.sms;


import cn.emay.sdk.client.api.Client;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SingletonClient {

    private static Client client = null;

    private SingletonClient() {
    }

    public synchronized static Client getClient(String softwareSerialNo, String key) {
        if (client == null) {
            try {
                client = new Client(softwareSerialNo, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public synchronized static Client getClient() {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("config");
        if (client == null) {
            try {
                client = new Client(bundle.getString("sms.softwareSerialNo"), bundle.getString("sms.key"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public static void main(String str[]) {
        SingletonClient.getClient();
    }
}
