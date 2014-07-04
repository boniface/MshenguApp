package zm.hashcode.android.mshengu.connection;

import java.io.Serializable;

/**
 * Created by boniface on 2013/12/18.
 */
public class MobileResponseMessage implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
