package com.onekeyask.lawyer.global;

/**
 * Created by zht on 2017/04/19 17:07
 */

public class Constant {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 没有数据
     */
    public static final int EMPTY = -3;
    /**
     * 没有更多数据
     */
    public static final int NOMORE= -4;

    /**
     * 网络中断，请检查您的网络状态
     */
    public static final int NETERROR= -1000;
    /**
     * 未知错误
     */
    public static final int UNKONWERROR= -1001;
    /**
    * 由聊天页面back回main
    */
    public static boolean goService = false;

    public static int lawyerId = 0;

    public static int userId = 0;
    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";

    public static String WeChatAppID;
    public static String ChatId = "";

    public static String tags = "{\"allSpecial\":[" +
            "{\"id\":1,\"name\":\"合同纠纷\"}," +
            "{\"id\":2,\"name\":\"房产纠纷\"}," +
            "{\"id\":3,\"name\":\"婚姻继承\"}," +
            "{\"id\":4,\"name\":\"人身损害\"}," +
            "{\"id\":5,\"name\":\"劳动争议\"}," +
            "{\"id\":6,\"name\":\"债权债务\"}," +
            "{\"id\":7,\"name\":\"侵权纠纷\"}," +
            "{\"id\":8,\"name\":\"消费维权\"}," +
            "{\"id\":9,\"name\":\"交通事故\"}," +
            "{\"id\":10,\"name\":\"刑事辩护\"}," +
            "{\"id\":12,\"name\":\"投资融资\"}," +
            "{\"id\":13,\"name\":\"涉外\"}," +
            "{\"id\":14,\"name\":\"兼并收购\"}," +
            "{\"id\":15,\"name\":\"上市\"}," +
            "{\"id\":16,\"name\":\"知识产权\"}," +
            "{\"id\":17,\"name\":\"新三板\"}]}";

}
