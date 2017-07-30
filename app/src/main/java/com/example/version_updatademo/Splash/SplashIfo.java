package com.example.version_updatademo.Splash;

/**
 * Created by on 2017/7/25.
 */

public class SplashIfo {
    /**
     * status : 200
     * data : {"text_title":"简书-创作你的创作","img_icon":"http://img5.imgtn.bdimg.com/it/u=359150190,2955201467&fm=26&gp=0.jpg"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * text_title : 简书-创作你的创作
         * img_icon : http://img5.imgtn.bdimg.com/it/u=359150190,2955201467&fm=26&gp=0.jpg
         */

        private String text_title;
        private String img_icon;

        public String getText_title() {
            return text_title;
        }

        public void setText_title(String text_title) {
            this.text_title = text_title;
        }

        public String getImg_icon() {
            return img_icon;
        }

        public void setImg_icon(String img_icon) {
            this.img_icon = img_icon;
        }
    }
}
