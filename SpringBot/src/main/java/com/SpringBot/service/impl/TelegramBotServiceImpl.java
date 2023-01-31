package com.SpringBot.service.impl;

import com.SpringBot.service.TelegramBotService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {
    @Override
    public void keyboard(SendMessage message) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton=new KeyboardButton();
        keyboardButton.setText("Случайный фильм " + "\uD83C\uDF9E");
        keyboardRow.add(keyboardButton);
        keyboardRow.add("Случайный сериал " + "\uD83C\uDFAC");
        keyboardRows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("Случайный фильм/сериал по жанрам " + "\uD83D\uDD6E");
        keyboardRows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("Посмотреть подборки");
        keyboardRows.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);


    }

}
