package delivery.box.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import delivery.box.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
