package delivery.box.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "box.loading")
public class BoxLoadingProperties {

    @Min(0)
    @Max(100)
    private int minBatteryPercent = 25;

    public int getMinBatteryPercent() {
        return minBatteryPercent;
    }

    public void setMinBatteryPercent(int minBatteryPercent) {
        this.minBatteryPercent = minBatteryPercent;
    }
}

