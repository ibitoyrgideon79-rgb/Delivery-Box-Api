package delivery.box.dto;

public class BatteryResponse {

    private Long boxId;
    private Integer batteryCapacity;

    public BatteryResponse() {
    }

    public BatteryResponse(Long boxId, Integer batteryCapacity) {
        this.boxId = boxId;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
