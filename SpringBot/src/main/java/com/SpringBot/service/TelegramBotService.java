package com.SpringBot.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@Slf4j
public class TelegramBotService {

//    public void startCommandReceived(long chatId, String name) {
//        String answer = "Hi, " + name + ", nice to meet you!";
//        log.info("Replied to user " + name);
//        sendMessage(chatId, answer);
//    }
//
//    public void sendMessage(long chatId, String textToSend) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(textToSend);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            log.error("Error occurred: " + e.getMessage());
//
//        }
//    }
}
