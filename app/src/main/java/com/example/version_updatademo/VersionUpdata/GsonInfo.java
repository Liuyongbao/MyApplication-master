package com.example.version_updatademo.VersionUpdata;

/**
 * Created by on 2017/7/21.
 */

public class GsonInfo {
    /**
     * status : 200
     * data : {"version":"2.3.1","info":"添加了支付功能，添加了用户的定位功能...","appurl":"https: //www.baidu.com/2.3.1.apk"}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
         * version : 2.3.1
         * info : 添加了支付功能，添加了用户的定位功能...
         * appurl : https: //www.baidu.com/2.3.1.apk
         */

        private String version;
        private String info;
        private String appurl;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAppurl() {
            return appurl;
        }

        public void setAppurl(String appurl) {
            this.appurl = appurl;
        }
    }
}
