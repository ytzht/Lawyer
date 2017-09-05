package com.onekeyask.lawyer.global;

/**
 * Created by zht on 2017/04/07 9:51
 */

public class BaseEvent {

    public static final int GO_SERVICE = 100;//由聊天页面back回main
    public static final int GO_DISCOVER = 200;//首页点击更多去发现页面



    public static BaseEvent event(int code){
        return new BaseEvent(code);
    }

    public static BaseEvent event(int code,Object assign){
        return new BaseEvent(code,assign);
    }
    
    private int code = 0;
    private Object assign;
    
    public BaseEvent(int code){
        this(code, null);
    }
    
    public BaseEvent(int code, Object assign) {
        this.code = code;
        this.assign = assign;
    }

    public int getCode() {
        return code;
    }

    public Object getAssign() {
        return assign;
    }
    
    
}
