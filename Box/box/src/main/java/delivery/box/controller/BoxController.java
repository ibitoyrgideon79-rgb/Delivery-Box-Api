package delivery.box.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import delivery.box.dto.BatteryResponse;
import delivery.box.dto.BoxResponse;
import delivery.box.dto.CreateBoxRequest;
import delivery.box.dto.ItemResponse;
import delivery.box.dto.LoadItemsRequest;
import delivery.box.service.BoxService;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoxResponse create(@Valid @RequestBody CreateBoxRequest request) {
        return boxService.createBox(request);
    }

    @PostMapping("/{boxId}/load")
    public BoxResponse load(@PathVariable long boxId, @Valid @RequestBody LoadItemsRequest request) {
        return boxService.loadItems(boxId, request.getItems());
    }

    @GetMapping("/{boxId}/items")
    public List<ItemResponse> loadedItems(@PathVariable long boxId) {
        return boxService.getLoadedItems(boxId);
    }

    @GetMapping("/available")
    public List<BoxResponse> available() {
        return boxService.getAvailableBoxesForLoading();
    }

    @GetMapping("/{boxId}/battery")
    public BatteryResponse battery(@PathVariable long boxId) {
        return boxService.getBatteryLevel(boxId);
    }
}

