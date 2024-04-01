package com.example.backEndreportScheduler.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backEndreportScheduler.model.LocoStatusSummaryModel;
import com.example.backEndreportScheduler.model.LocomotifInfoModel;
import com.example.backEndreportScheduler.model.LocomotifSummaryModel;
import com.example.backEndreportScheduler.repository.LocoStatusSummaryRepos;
import com.example.backEndreportScheduler.repository.LocomotifSummaryRepos;

@Service("myMongoService")
public class MongoService {
    private static final Logger logger = LoggerFactory.getLogger(MongoService.class);

    private final MongoTemplate mongoTemplate;
    private final LocomotifSummaryRepos locomotifSummaryRepository;
    private final LocoStatusSummaryRepos lokoStatusSummaryRepository;

    public MongoService(MongoTemplate mongoTemplate, 
            LocomotifSummaryRepos locomotifSummaryRepository, 
            LocoStatusSummaryRepos locoStatusSummaryRepository
            )
        {
        this.mongoTemplate = mongoTemplate;
        this.locomotifSummaryRepository = locomotifSummaryRepository;
        this.lokoStatusSummaryRepository = locoStatusSummaryRepository;
    }

    public List<LocomotifInfoModel> fetchDataFromMongo() {
        return mongoTemplate.findAll(LocomotifInfoModel.class);
    }

    public void resetAllNumbers() {
        lokoStatusSummaryRepository.resetAllNumbers();
    }

    @Scheduled(fixedRate = 3600000) // every hour
    public void fetchDataRegularly() {
        logger.info("Fetching data from MongoDB...");
        List<LocomotifInfoModel> data = fetchDataFromMongo();
        logger.info("Data fetched!");
        
        if (data != null && !data.isEmpty()) {
            int totalLocomotive = data.size();
            int totalActive = 0;
            int totalNotActive = 0;
            int totalMaintenance = 0;

            for (LocomotifInfoModel entity : data) {
                switch (entity.getStatus()) {
                    case "Active":
                        totalActive++;
                        break;
                    case "Not active":
                        totalNotActive++;
                        break;
                    case "Maintenance":
                        totalMaintenance++;
                        break;
                    default:
                        break;
                }
            }

            logger.info("/----------------------- Summary Data -----------------------/");
            logger.info("Total Locomotive: {}", totalLocomotive);
            logger.info("Total Active: {}", totalActive);
            logger.info("Total Not Active: {}", totalNotActive);
            logger.info("Total Maintenance: {}", totalMaintenance);

            LocomotifSummaryModel locoSummary = new LocomotifSummaryModel();
            locoSummary.setId(UUID.randomUUID().toString());
            locoSummary.setActive(totalActive);
            locoSummary.setNotActive(totalNotActive);
            locoSummary.setMaintenance(totalMaintenance);
            locoSummary.setUpdatedAt(LocalDateTime.now());
            locoSummary.setTotalLocomotive(totalLocomotive);
            locomotifSummaryRepository.save(locoSummary);

            /*Cleaning the values ​​of all integers in the summary_loco_status database table every restart, 
            so that the data matches the summary_loco table*/
            resetAllNumbers();

            List<LocoStatusSummaryModel> allStatus = lokoStatusSummaryRepository.findAll();          
            for (LocomotifInfoModel entity : data) {
                for (LocoStatusSummaryModel status : allStatus) {
                    if (status.getLocoName().equalsIgnoreCase(entity.getName())) {
                        switch (entity.getStatus()) {
                            case "Active":
                                status.setActive(status.getActive() + 1);
                                status.setTotal(status.getTotal() + 1);
                                status.setUpdatedAt(LocalDateTime.now());
                                lokoStatusSummaryRepository.save(status);
                                break;
                            case "Not active":
                                status.setNotActive(status.getNotActive() + 1);
                                status.setTotal(status.getTotal() + 1);
                                status.setUpdatedAt(LocalDateTime.now());
                                lokoStatusSummaryRepository.save(status);
                                break;
                            case "Maintenance":
                                status.setMaintenance(status.getMaintenance() + 1);
                                status.setTotal(status.getTotal() + 1);
                                status.setUpdatedAt(LocalDateTime.now());
                                lokoStatusSummaryRepository.save(status);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            
            logger.info("/----------------------- Status Summary Data -----------------------/");
            logger.info(allStatus.toString() + "\n");
        }
    }
}
