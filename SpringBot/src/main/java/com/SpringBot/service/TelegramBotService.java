package com.SpringBot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramBotService {
    void keyboard(SendMessage message);

}
