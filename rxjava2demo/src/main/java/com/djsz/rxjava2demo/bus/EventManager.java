package com.djsz.rxjava2demo.bus;

/**
 * author: Shawn
 * time  : 2017/4/14 09:45
 * desc  :
 */
public class EventManager {

    public static class ChangeTextEvent implements IEvent {
        public String text;

        public ChangeTextEvent(String text) {
            this.text = text;
        }
    }
}
