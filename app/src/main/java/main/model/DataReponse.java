package main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tonkhanh on 5/23/17.
 */

public class DataReponse {
    @SerializedName("data")
    @Expose
    private ListConpon data;

    public ListConpon getData() {
        return data;
    }

    public void setData(ListConpon data) {
        this.data = data;
    }
}
