package com.comsysto.agiledevstarter.config;

import com.comsysto.agiledevstarter.persistence.JpaBurgerRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = JpaBurgerRepository.class)
public class PersistenceConfig {
}
