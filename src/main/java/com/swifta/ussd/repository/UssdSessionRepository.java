package com.swifta.ussd.repository;

import com.swifta.ussd.entity.cache.UssdSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UssdSessionRepository extends CrudRepository<UssdSession, String> {
    List<UssdSession> findByMsisdn(String msisdn);
}
