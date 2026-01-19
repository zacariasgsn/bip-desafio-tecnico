package com.example.backend.config;

import com.example.ejb.service.BeneficioEjbService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EjbConfig {

    @Bean
    public BeneficioEjbService beneficioEjbService(EntityManager em) {
        BeneficioEjbService service = new BeneficioEjbService();
        service.setEntityManager(em);
        return service;
    }
}

