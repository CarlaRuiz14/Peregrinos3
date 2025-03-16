package com.luisdbb.tarea3AD2024base.repositorios;


import com.luisdbb.tarea3AD2024base.modelo.CarnetMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarnetMongoRepository extends MongoRepository<CarnetMongo, String> {
}
