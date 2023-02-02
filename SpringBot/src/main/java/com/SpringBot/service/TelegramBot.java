package com.SpringBot.service;

import com.SpringBot.config.BotConfig;;
import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.repository.GenreRepository;
import com.SpringBot.model.repository.TypeRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.SpringBot.constant.VarConstant.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final UserService userService;
    private final TelegramBotService botService;
    private final BotConfig botConfig;
    private final ProductService productService;
    private final GenreRepository genreRepository;
    private final TypeRepository typeRepository;
    private final GenreService genreService;

    public TelegramBot(UserService userService, TelegramBotService botService, BotConfig botConfig, ProductService productService, GenreRepository genreRepository, TypeRepository typeRepository, GenreService genreService) {
        this.userService = userService;
        this.botService = botService;
        this.botConfig = botConfig;
        this.productService = productService;
        this.genreRepository = genreRepository;
        this.typeRepository = typeRepository;
        this.genreService = genreService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case START:
                    userService.registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case HELP:
                    sendMessage(chatId, HELP_TEXT);
                    break;
                case SELECTION:
                    sendMessage(chatId, typeRepository.findAllTypes().toString());
                    break;
                case GENRES:
                    sendMessage(chatId, genreRepository.findAllGenres().toString());
                    log.info("command genres");
                    break;
                case MOVIE:
                    showFilm(chatId, productService.exampleMovie());
                    log.info("command movie");
                    break;
                case SERIES:
                    showFilm(chatId, productService.exampleSeries());
                    log.info("command series");
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
                    log.info("command was not recognized");
            }
        }
    }

    private void showFilm(long chatId, ProductDto product) {
        String answer = product.getType().getName() + " называется \"" + product.getTitle() + "\"" + "\n\n"
                + "Описание: " + product.getDescription() + "\n\n"
                + "Оценка: " + product.getRating() + "\n\n"
                + "Жанр: " + product.getGenres() + "\n\n"
                + product.getUrl() + "\n\n";
        sendMessage(chatId, answer);
    }


    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет, " + name + "! В этом боте ты сможешь найти фильм или сериал на вечер." +
                " Выбирай внизу категорию, подходящую тебе, и наслаждайся просмотром!" + ":blush:" + ":popcorn:");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        botService.keyboard(message);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }


}
