package com.shawn.yiyalearningdemo;

import java.util.List;

/**
 * author: Shawn
 * time  : 2017/2/21 10:23
 */

public class TestBean {

    /**
     * code : 20200
     * data : {"credit_list":[{"credit":10,"price":10},{"credit":20,"price":20},{"credit":30,"price":30}],
     * "credit_ruleinfo":"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet.
     * Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor.
     * Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum,    nulla
     * lacusctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget."}
     * env : test
     * msg : 成功
     * sys_time : 2017-02-20 17:40:00
     */

    private int code;
    /**
     * credit_list : [{"credit":10,"price":10},{"credit":20,"price":20},{"credit":30,"price":30}]
     * credit_ruleinfo : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet.
     * Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor.
     * Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum,    nulla
     * lacusctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget.
     */

    private DataEntity data;
    private String env;
    private String msg;
    private String sys_time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }

    public static class DataEntity {
        private String credit_ruleinfo;
        /**
         * credit : 10
         * price : 10
         */

        private List<CreditListEntity> credit_list;

        public String getCredit_ruleinfo() {
            return credit_ruleinfo;
        }

        public void setCredit_ruleinfo(String credit_ruleinfo) {
            this.credit_ruleinfo = credit_ruleinfo;
        }

        public List<CreditListEntity> getCredit_list() {
            return credit_list;
        }

        public void setCredit_list(List<CreditListEntity> credit_list) {
            this.credit_list = credit_list;
        }

        public static class CreditListEntity {
            private int credit;
            private int price;

            public int getCredit() {
                return credit;
            }

            public void setCredit(int credit) {
                this.credit = credit;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
