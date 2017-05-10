package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/04/18 9:37
 */

public class IsFavorite {


    /**
     * err : {"code":0,"msg":"正常","eventid":"xxxx-xxxx-xxxx"}
     *  favorite  : false
     */
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}
