package zm.hashcode.android.mshengu.resources;

import java.io.Serializable;

/**
 * Created by hashcode on 2014/07/03.
 */
public class TruckResources implements Serializable {
    private String id;
    private String numberPlate;
    private String vehicleNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }


}
