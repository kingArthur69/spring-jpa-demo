package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PlaceRepository extends CrudRepository<Place, UUID> {
}
