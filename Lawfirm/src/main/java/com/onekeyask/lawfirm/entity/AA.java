package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/16 15:54
 */

public class AA {

    /**
     * timestamp : 1502869860
     * production_mode : false
     * appkey : 58feecefaed179145300072d
     * payload : {"body":{"text":"您的积分增加了300，请到我的积分中查看。","title":null,"ticker":null,"sound":"default","after_open":"go_activity","custom":{"data":"","activity":"UserPointsChangeNotifaction"}},"display_type":"message"}
     * device_tokens : Ai-C2_uwh_FmLxLPJIEVreZVW5tQDQPsd1sFGnDNczWu
     * type : unicast
     */

    private String timestamp;
    private String production_mode;
    private String appkey;
    private PayloadBean payload;
    private String device_tokens;
    private String type;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProduction_mode() {
        return production_mode;
    }

    public void setProduction_mode(String production_mode) {
        this.production_mode = production_mode;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public String getDevice_tokens() {
        return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
        this.device_tokens = device_tokens;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class PayloadBean {
        /**
         * body : {"text":"您的积分增加了300，请到我的积分中查看。","title":null,"ticker":null,"sound":"default","after_open":"go_activity","custom":{"data":"","activity":"UserPointsChangeNotifaction"}}
         * display_type : message
         */

        private BodyBean body;
        private String display_type;

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        public String getDisplay_type() {
            return display_type;
        }

        public void setDisplay_type(String display_type) {
            this.display_type = display_type;
        }

        public static class BodyBean {
            /**
             * text : 您的积分增加了300，请到我的积分中查看。
             * title : null
             * ticker : null
             * sound : default
             * after_open : go_activity
             * custom : {"data":"","activity":"UserPointsChangeNotifaction"}
             */

            private String text;
            private Object title;
            private Object ticker;
            private String sound;
            private String after_open;
            private CustomBean custom;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getTicker() {
                return ticker;
            }

            public void setTicker(Object ticker) {
                this.ticker = ticker;
            }

            public String getSound() {
                return sound;
            }

            public void setSound(String sound) {
                this.sound = sound;
            }

            public String getAfter_open() {
                return after_open;
            }

            public void setAfter_open(String after_open) {
                this.after_open = after_open;
            }

            public CustomBean getCustom() {
                return custom;
            }

            public void setCustom(CustomBean custom) {
                this.custom = custom;
            }

            public static class CustomBean {
                /**
                 * data :
                 * activity : UserPointsChangeNotifaction
                 */

                private String data;
                private String activity;

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

                public String getActivity() {
                    return activity;
                }

                public void setActivity(String activity) {
                    this.activity = activity;
                }
            }
        }
    }
}
