package com.warehouse.ware.repository;

import com.warehouse.ware.entity.Movment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
// import java.util.Optional;

@Repository
public interface MovementRepository extends MongoRepository<Movment, String> {
}
