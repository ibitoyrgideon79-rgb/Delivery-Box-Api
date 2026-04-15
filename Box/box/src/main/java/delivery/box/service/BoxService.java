package delivery.box.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import delivery.box.config.BoxLoadingProperties;
import delivery.box.dto.BatteryResponse;
import delivery.box.dto.BoxResponse;
import delivery.box.dto.CreateBoxRequest;
import delivery.box.dto.ItemRequest;
import delivery.box.dto.ItemResponse;
import delivery.box.entity.Box;
import delivery.box.entity.BoxState;
import delivery.box.entity.Item;
import delivery.box.exception.BusinessRuleException;
import delivery.box.exception.NotFoundException;
import delivery.box.repository.BoxRepository;
import delivery.box.repository.ItemRepository;

@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;
    private final BoxLoadingProperties loadingProperties;

    public BoxService(BoxRepository boxRepository, ItemRepository itemRepository, BoxLoadingProperties loadingProperties) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
        this.loadingProperties = loadingProperties;
    }

    @Transactional
    public BoxResponse createBox(CreateBoxRequest request) {
        Box box = new Box();
        box.setTxref(request.getTxref());
        box.setWeightLimit(request.getWeightLimit());
        box.setBatteryCapacity(request.getBatteryCapacity());
        box.setState(BoxState.IDLE);

        Box saved = boxRepository.save(box);
        return toBoxResponse(saved, List.of());
    }

    @Transactional
    public BoxResponse loadItems(long boxId, List<ItemRequest> itemRequests) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Box not found: " + boxId));

        int minBattery = loadingProperties.getMinBatteryPercent();
        if (box.getBatteryCapacity() == null || box.getBatteryCapacity() < minBattery) {
            throw new BusinessRuleException("Box battery is below " + minBattery + "% and cannot be loaded");
        }

        // Only allow loading when the box is in a sensible pre-delivery state.
        if (box.getState() != BoxState.IDLE && box.getState() != BoxState.LOADING && box.getState() != BoxState.LOADED) {
            throw new BusinessRuleException("Box is not available for loading in state: " + box.getState());
        }

        int existingWeight = box.getItems().stream()
                .map(Item::getWeight)
                .filter(w -> w != null)
                .reduce(0, Integer::sum);

        int newWeight = itemRequests.stream()
                .map(ItemRequest::getWeight)
                .filter(w -> w != null)
                .reduce(0, Integer::sum);

        int total = existingWeight + newWeight;
        if (box.getWeightLimit() == null) {
            throw new BusinessRuleException("Box weight limit is not set");
        }
        if (total > box.getWeightLimit()) {
            throw new BusinessRuleException(
                    "Total item weight (" + total + "g) exceeds box weight limit (" + box.getWeightLimit() + "g)");
        }

        box.setState(BoxState.LOADING);

        for (ItemRequest req : itemRequests) {
            Item item = new Item();
            item.setName(req.getName());
            item.setWeight(req.getWeight());
            item.setCode(req.getCode());
            box.addItem(item);
        }

        box.setState(BoxState.LOADED);
        Box saved = boxRepository.save(box);

        List<ItemResponse> items = saved.getItems().stream()
                .sorted(Comparator.comparing(Item::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::toItemResponse)
                .toList();

        return toBoxResponse(saved, items);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getLoadedItems(long boxId) {
        // Fast path: query items directly instead of relying on lazy initialization outside a transaction.
        boxRepository.findById(boxId).orElseThrow(() -> new NotFoundException("Box not found: " + boxId));
        return itemRepository.findByBoxId(boxId).stream()
                .sorted(Comparator.comparing(Item::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::toItemResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoxResponse> getAvailableBoxesForLoading() {
        int minBattery = loadingProperties.getMinBatteryPercent();
        return boxRepository.findByStateAndBatteryCapacityGreaterThanEqual(BoxState.IDLE, minBattery).stream()
                .map(box -> toBoxResponse(box, List.of()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BatteryResponse getBatteryLevel(long boxId) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Box not found: " + boxId));
        return new BatteryResponse(box.getId(), box.getBatteryCapacity());
    }

    private BoxResponse toBoxResponse(Box box, List<ItemResponse> items) {
        return new BoxResponse(
                box.getId(),
                box.getTxref(),
                box.getWeightLimit(),
                box.getBatteryCapacity(),
                box.getState(),
                items
        );
    }

    private ItemResponse toItemResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getWeight(), item.getCode());
    }
}
