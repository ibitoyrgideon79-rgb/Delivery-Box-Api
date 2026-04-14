package delivery.box.dto;

import java.util.List;

import delivery.box.entity.BoxState;

public class BoxResponse {

    private Long id;
    private String txref;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private BoxState state;
    private List<ItemResponse> items;

    public BoxResponse() {
    }

    public BoxResponse(Long id, String txref, Integer weightLimit, Integer batteryCapacity, BoxState state,
            List<ItemResponse> items) {
        this.id = id;
        this.txref = txref;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxref() {
        return txref;
    }

    public void setTxref(String txref) {
        this.txref = txref;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public BoxState getState() {
        return state;
    }

    public void setState(BoxState state) {
        this.state = state;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }
}
