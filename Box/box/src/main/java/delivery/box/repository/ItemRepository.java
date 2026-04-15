package delivery.box.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.box.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByBoxId(Long boxId);
}
