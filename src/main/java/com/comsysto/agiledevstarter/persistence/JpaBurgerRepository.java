package com.comsysto.agiledevstarter.persistence;

import com.comsysto.agiledevstarter.domain.Burger;
import org.springframework.data.repository.CrudRepository;

public interface JpaBurgerRepository extends CrudRepository<Burger, Long> {
}
