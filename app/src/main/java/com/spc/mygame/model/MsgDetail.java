package com.spc.mygame.model;

public class MsgDetail {


    public boolean isLogin = false;
    public boolean isOwn = false;
    public Linfo linfo;
    public Rinfo rinfo;
    public Minfo minfo;

    public class Linfo {
        public String title;
        public String content;
        public String photo;
        public String sort;
        public String dpname;
        public String dsname;
        public String status;
        public String create_time;
    }

    public class Rinfo {
        public String content;
        public String create_time;
        public String name;
    }

    public class Minfo {
        public String content;
        public String create_time;
        public int mark;
    }
}
