package delivery.box.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import delivery.box.dto.CreateBoxRequest;
import delivery.box.dto.ItemRequest;
import delivery.box.exception.BusinessRuleException;

@SpringBootTest
@Transactional
class BoxServiceTest {

    @Autowired
    private BoxService boxService;

    @Test
    void cannotLoadWhenBatteryBelowThreshold() {
        long boxId = boxService.createBox(createBox("LOWBAT", 500, 10)).getId();
        assertThrows(BusinessRuleException.class, () -> boxService.loadItems(boxId, List.of(item("A", 10, "A_1"))));
    }

    @Test
    void cannotLoadAboveWeightLimit() {
        long boxId = boxService.createBox(createBox("WGT", 100, 90)).getId();
        assertThrows(BusinessRuleException.class,
                () -> boxService.loadItems(boxId, List.of(item("A", 60, "A_1"), item("B", 60, "B_2"))));
    }

    private static CreateBoxRequest createBox(String txref, int limit, int battery) {
        CreateBoxRequest req = new CreateBoxRequest();
        req.setTxref(txref);
        req.setWeightLimit(limit);
        req.setBatteryCapacity(battery);
        return req;
    }

    private static ItemRequest item(String name, int weight, String code) {
        ItemRequest req = new ItemRequest();
        req.setName(name);
        req.setWeight(weight);
        req.setCode(code);
        return req;
    }
}

