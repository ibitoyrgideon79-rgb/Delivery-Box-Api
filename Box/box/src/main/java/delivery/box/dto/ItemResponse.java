package delivery.box.dto;

public class ItemResponse {

    private Long id;
    private String name;
    private Integer weight;
    private String code;

    public ItemResponse() {
    }

    public ItemResponse(Long id, String name, Integer weight, String code) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
