package delivery.box.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import delivery.box.entity.Box;
import delivery.box.entity.BoxState;

public interface BoxRepository extends JpaRepository<Box, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<Box> findById(Long id);

    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(BoxState state, Integer batteryCapacity);
}
