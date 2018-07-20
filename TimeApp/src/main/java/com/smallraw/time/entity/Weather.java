package com.smallraw.time.entity;

/**
 * @author QuincySx
 * @date 2018/7/19 上午10:48
 */
public class Weather {

    /**
     * place : 朝阳
     * parent_city : 北京
     * admin_area : 北京
     * cloud : 75
     * cond_code : 104
     * cond_txt : 阴
     * fl : 27
     * tmp : 24
     * pcpn : 0.0
     */

    private String place;
    private String parent_city;
    private String admin_area;
    private String cloud;
    private String cond_code;
    private String cond_txt;
    private String fl;
    private String tmp;
    private String pcpn;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getParent_city() {
        return parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCond_code() {
        return cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }
}
