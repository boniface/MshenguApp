package zm.hashcode.android.mshengu.resources;

/**
 * Created with IntelliJ IDEA.
 * User: chaitanya
 * Date: 2013/10/15
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;

public class GeoplotResource implements Serializable {
    private String sitename;
    private String latitude;
    private String longitude;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}

