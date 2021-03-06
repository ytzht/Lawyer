package com.onekeyask.lawfirm.global;

import com.onekeyask.lawfirm.entity.GetRed;

/**
 * Created by zht on 2017/04/07 9:51
 */

public class BaseEvent {

    public static final int GO_SERVICE = 100;//由聊天页面back回main
    public static final int FINISH_MAIN = 200;//mainActivity finish();
    public static final int FINISH_SETTING = 300;//mainActivity finish();
    public static final int FINISH_TALKING = 400;//TalkingActivity finish();
    public static final int FINISH_Identity = 500;//TalkingActivity finish();
    public static final int AddBankCard = 600;// finish();
    public static final int TXState = 700;// finish();
    public static final int NOTIFICATION_MSG = 800;// 有推送消息
    public static GetRed getRed;

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
