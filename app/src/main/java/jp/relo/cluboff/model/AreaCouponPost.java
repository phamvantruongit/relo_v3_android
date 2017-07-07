package jp.relo.cluboff.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jp.relo.cluboff.util.Constant;

/**
 * Created by tonkhanh on 7/6/17.
 */

public class AreaCouponPost {
    private String p_s1;
    private String p_s6;
    private String p_s7;
    private String p_s22;
    private String p_s25;
    private String p_s29;
    private String p_s34;
    private String p_s36;

    public AreaCouponPost() {
        this.p_s1 = "0";
        this.p_s6 = "1";
        this.p_s7 = Constant.ACC_TEST_ID_LOGIN;
        this.p_s22 = "1";
        this.p_s25 = "90";
        this.p_s29 = "1";
        this.p_s34 = "1";
        this.p_s36 = "25,27,123,124,130,131,32,188,33,46,126,175,177";
    }

    public String getP_s1() {
        return p_s1;
    }

    public void setP_s1(String p_s1) {
        this.p_s1 = p_s1;
    }

    public String getP_s6() {
        return p_s6;
    }

    public void setP_s6(String p_s6) {
        this.p_s6 = p_s6;
    }

    public String getP_s7() {
        return p_s7;
    }

    public void setP_s7(String p_s7) {
        this.p_s7 = p_s7;
    }

    public String getP_s22() {
        return p_s22;
    }

    public void setP_s22(String p_s22) {
        this.p_s22 = p_s22;
    }

    public String getP_s25() {
        return p_s25;
    }

    public void setP_s25(String p_s25) {
        this.p_s25 = p_s25;
    }

    public String getP_s29() {
        return p_s29;
    }

    public void setP_s29(String p_s29) {
        this.p_s29 = p_s29;
    }

    public String getP_s34() {
        return p_s34;
    }

    public void setP_s34(String p_s34) {
        this.p_s34 = p_s34;
    }

    public String getP_s36() {
        return p_s36;
    }

    public void setP_s36(String p_s36) {
        this.p_s36 = p_s36;
    }

    @Override
    public String toString() {
        try {
            return "p_s1=" + URLEncoder.encode(p_s1, "UTF-8") +
                    "&p_s6=" + URLEncoder.encode(p_s6, "UTF-8") +
                    "&p_s7=" + URLEncoder.encode(p_s7, "UTF-8") +
                    "&p_s22=" + URLEncoder.encode(p_s22, "UTF-8") +
                    "&p_s25=" + URLEncoder.encode(p_s25, "UTF-8") +
                    "&p_s29=" + URLEncoder.encode(p_s29, "UTF-8") +
                    "&p_s34=" + URLEncoder.encode(p_s34, "UTF-8") +
                    "&p_s36=" + URLEncoder.encode(p_s36, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        }
    }
}