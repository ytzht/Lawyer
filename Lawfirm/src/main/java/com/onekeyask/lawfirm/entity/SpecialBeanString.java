package com.onekeyask.lawfirm.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by zht on 2017/08/11 9:16
 */

public class SpecialBeanString {

    public static List<SpecialBean.DataBean.AllSpecialBean> getSpecial(){
        String special = "{\"code\":0,\"msg\":\"正常\",\"data\":{\"allSpecial\":[{\"id\":11,\"name\":\"全部\"},{\"id\":1,\"name\":\"合同纠纷\"},{\"id\":2,\"name\":\"房产纠纷\"},{\"id\":3,\"name\":\"婚姻继承\"},{\"id\":4,\"name\":\"人身损害\"},{\"id\":5,\"name\":\"劳动争议\"},{\"id\":6,\"name\":\"债权债务\"},{\"id\":7,\"name\":\"侵权纠纷\"},{\"id\":8,\"name\":\"消费维权\"},{\"id\":9,\"name\":\"交通事故\"},{\"id\":10,\"name\":\"刑事辩护\"},{\"id\":12,\"name\":\"投资\"},{\"id\":13,\"name\":\"融资\"},{\"id\":14,\"name\":\"兼并收购\"},{\"id\":15,\"name\":\"上市\"},{\"id\":16,\"name\":\"知识产权\"},{\"id\":17,\"name\":\"新三板\"}]}}\n";

        SpecialBean category = (new Gson()).fromJson(special, SpecialBean.class);

        return category.getData().getAllSpecial();

    }
}
