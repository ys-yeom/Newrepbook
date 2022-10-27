package com.example.newrepbook.practice;

public class memo_list {
    private String profile;
    private String name; //제목
    private String comment; //내용
    private String staffkey;

    public memo_list() {}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStaffkey() {
        return staffkey;
    }

    public void setStaff(String staffkey) {
        this.staffkey = staffkey;
    }

    //값을 추가할때 쓰는 함수
    public memo_list(String profile, String name, String comment, String staffkey){
        this.profile = profile;
        this.name = name;
        this.comment = comment;
        this.staffkey = staffkey;
    }
}
