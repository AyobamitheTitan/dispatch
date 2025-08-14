package com.polarisdigitech.assessment.dispatch.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.polarisdigitech.assessment.dispatch.entities.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findAllByBoxId(Long boxId);
}
