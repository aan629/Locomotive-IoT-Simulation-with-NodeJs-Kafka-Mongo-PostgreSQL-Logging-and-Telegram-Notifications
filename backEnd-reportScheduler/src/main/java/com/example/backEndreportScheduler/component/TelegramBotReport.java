package com.example.backEndreportScheduler.component;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.example.backEndreportScheduler.model.LocoStatusSummaryModel;
import com.example.backEndreportScheduler.model.LocomotifSummaryModel;
import com.example.backEndreportScheduler.service.TelegramService;

@Component
@DependsOn("myMongoService")
public class TelegramBotReport extends TelegramLongPollingBot {
    private TelegramService telegramService;
    private static final String BOT_TOKEN = "7081445935:AAHYiFTuHqmjng7hPtaIMUMHQRjGg0ljVPk";
    private static final String CHAT_ID = "5492170728";

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotReport.class);

    @Override
    public void onUpdateReceived(Update update) {

    }

    @SuppressWarnings("deprecation")
    public TelegramBotReport(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Scheduled(fixedRate = 3600000) // 1 hour
    public void sendLatestData() {

        LocomotifSummaryModel latestDataSummary = telegramService.getLatestSummary();
        List<LocoStatusSummaryModel> latestDataSummaryStatus = telegramService.getLatestSummaryStatus();
        sendMessageToTelegramBot(latestDataSummary, latestDataSummaryStatus);
    }

    private void sendMessageToTelegramBot(LocomotifSummaryModel data, List<LocoStatusSummaryModel> dataStatus) {
        String message =  "*New Summary Data:*\n" + 
                        "Total Locomotive: " + data.getTotalLocomotive() +                      
                        "\nTotal Active: " + data.getActive() + 
                        "\nTotal Not Active: " + data.getNotActive() + 
                        "\nTotal Maintenance: " + data.getMaintenance() +
                        "\nDate and Time: " + data.getUpdatedAt() + "\n\n" + 
                        "*New Summary Data Status:*\n" + dataStatus;

        SendMessage request = new SendMessage();
            request.setChatId(CHAT_ID);
            request.setParseMode(ParseMode.MARKDOWN);
            request.setText(message);

        try {
            execute(request);
            logger.info("Message sent to Telegram succesfully!!!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        //Return your bot's username
        return "@my_locomotive_bot";
    }

    @Override
    public String getBotToken() {
        //Return your bot's token
        return BOT_TOKEN;
    }
}
