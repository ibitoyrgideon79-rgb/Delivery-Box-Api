package delivery.box.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ItemRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    private String name;

    @NotNull
    @Min(0)
    private Integer weight;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

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
