package eu.codlab.swtor.ui.service;

import eu.codlab.swtor.internal.database.impl.Key;

/**
 * Created by kevinleperf on 15/02/16.
 */
public class SendMessageEvent {

    private Key mKey;

    private SendMessageEvent() {

    }

    public SendMessageEvent(Key key) {
        this();
        mKey = key;
    }

    public Key getKey() {
        return mKey;
    }
}
