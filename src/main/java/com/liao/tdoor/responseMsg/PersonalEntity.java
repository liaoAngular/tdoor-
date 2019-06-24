package com.liao.tdoor.responseMsg;

import com.liao.tdoor.model.*;

import java.util.List;

/**
 * 个人中心信息
 * @author 廖某某
 * @date 2019/2/27 14:58
 **/
public class PersonalEntity {
    private User user_info;
    private List<Posting> post_info;
    private List<Object> collect;
    private int self;
    public PersonalEntity(){

    }
    public PersonalEntity(User user_info,List<Posting> post_info,int self){
        this.user_info=user_info;
        this.post_info=post_info;
        this.self=self;
    }
    public PersonalEntity(List<Object> collect){
        this.collect=collect;
    }

    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(User user_info) {
        this.user_info = user_info;
    }

    public List<Posting> getPost_info() {
        return post_info;
    }

    public void setPost_info(List<Posting> post_info) {
        this.post_info = post_info;
    }

    public List<Object> getCollect() {
        return collect;
    }

    public void setCollect(List<Object> collect) {
        this.collect = collect;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "PersonalEntity{" +
                "user_info=" + user_info +
                ", post_info=" + post_info +
                ", collect=" + collect +
                ", self=" + self +
                '}';
    }
}
