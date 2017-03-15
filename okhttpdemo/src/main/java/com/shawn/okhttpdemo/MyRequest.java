package com.shawn.okhttpdemo;

/**
 * author: Shawn
 * time  : 2016/12/15 12:57
 */

public class MyRequest {
    private int page;
    private int rows;

    public MyRequest(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }
}
