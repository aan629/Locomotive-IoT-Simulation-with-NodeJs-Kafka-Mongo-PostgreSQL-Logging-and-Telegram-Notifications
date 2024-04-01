package com.example.backEndinfoScheduler.component;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.backEndinfoScheduler.service.LocoService;

@Component
public class LocoScheduler {
    
    private LocoService locoService;

    public LocoScheduler (LocoService locoService) {
        this.locoService = locoService;
    }

    @Async
    @Scheduled(fixedRate = 10000) // Dalam MiliSecond / Ms
    public void sendLocoData() {
        locoService.sendDataToNodeJS();
    }
}
