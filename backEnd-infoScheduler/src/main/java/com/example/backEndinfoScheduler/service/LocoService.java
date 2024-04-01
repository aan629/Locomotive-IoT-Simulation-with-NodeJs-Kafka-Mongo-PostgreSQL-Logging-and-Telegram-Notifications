package com.example.backEndinfoScheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

@Service
public class LocoService {
    private RestTemplate restTemplate = new RestTemplate();
    private Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(LocoService.class);

    private double formatDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public void sendDataToNodeJS() {
        String[] possibleLocoName = {"Loco 1", "Loco 2", "Loco 3", "Loco 4", "Loco 5", "Loco 6", "Loco 7", "Loco 8", "Loco 9", "Loco 10"};
        String[] possibleStatus = {"Active", "Not active", "Maintenance"};

        String locoName = possibleLocoName[random.nextInt(possibleLocoName.length)];
        String status = possibleStatus[random.nextInt(possibleStatus.length)];

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);

        Map<String, Object> data = new HashMap<>();
        data.put("Code", UUID.randomUUID());
        data.put("Name", locoName);
        data.put("Dimension", 
            formatDouble(random.nextDouble() * 100) + " x " + 
            formatDouble(random.nextDouble() * 100) + " x " + 
            formatDouble(random.nextDouble() * 100));
        data.put("Status", status);
        data.put("DateAndTime", dateTimeString);

        logger.info("Dummy data has been created and is ready to be sent to Node JS:\n"
            + data.toString());

        String url = "http://localhost:3001/receive-data";
        restTemplate.postForEntity(url, data, String.class);
    }
}
