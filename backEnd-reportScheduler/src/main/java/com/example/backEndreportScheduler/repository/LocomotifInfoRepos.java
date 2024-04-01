package com.example.backEndreportScheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.backEndreportScheduler.model.LocomotifInfoModel;

@Repository
public interface LocomotifInfoRepos extends MongoRepository<LocomotifInfoModel, String>{
    
}
