package jp.relo.cluboff.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jp.relo.cluboff.util.Constant;

/**
 * Created by tonkhanh on 7/6/17.
 */

public class MemberPost {
    private String u;
    private String COA_APP;

    public MemberPost() {

    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getCOA_APP() {
        return COA_APP;
    }

    public void setCOA_APP(String COA_APP) {
        this.COA_APP = COA_APP;
    }

    @Override
    public String toString() {
        try {
            return "u=" + URLEncoder.encode(u, "UTF-8") +
                    "&COA_APP=" + URLEncoder.encode(COA_APP, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
