package delivery.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.box.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
