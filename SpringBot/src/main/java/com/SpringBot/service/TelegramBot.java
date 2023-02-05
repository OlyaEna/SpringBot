package com.SpringBot.service;

import com.SpringBot.config.BotConfig;;
import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.entity.Genre;
import com.SpringBot.model.entity.Product;
import com.SpringBot.model.entity.Selection;
import com.SpringBot.model.repository.GenreRepository;
import com.SpringBot.model.repository.ProductRepository;
import com.SpringBot.model.repository.SelectionRepository;
import com.SpringBot.model.repository.TypeRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    private final SelectionRepository selectionRepository;
    private final ProductRepository productRepository;


    public TelegramBot(UserService userService, TelegramBotService botService, BotConfig botConfig, ProductService productService, GenreRepository genreRepository, TypeRepository typeRepository, GenreService genreService, SelectionRepository selectionRepository, ProductRepository productRepository) {
        this.userService = userService;
        this.botService = botService;
        this.botConfig = botConfig;
        this.productService = productService;
        this.genreRepository = genreRepository;
        this.typeRepository = typeRepository;
        this.genreService = genreService;
        this.selectionRepository = selectionRepository;
        this.productRepository = productRepository;
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
                    showAllSelections(chatId);
                    break;
                case GENRES:
                    showAllGenres(chatId);
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
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            executeEditMessageText(callbackData, chatId, messageId);
//            if (callbackData.equals(BUTTON_SELECTION)){
//                showFilmsBySelections(chatId);
//            }
        }
    }

    private void showFilm(long chatId, ProductDto product) {
        String answer = "\uD83D\uDCFD " + product.getType().getName() + " \"" + product.getTitle() + "\" " + "(" + product.getYear() + ")" + "\n\n"
                + "📜" + "Жанр: " + genreRepository.findGenresNamesById(genreRepository.findGenresNames(product.getId()))
                .toString().replaceAll("^\\[|\\]$", "") + "\n\n"
                + "\uD83C\uDFC6 " + "Оценка: " + product.getRating() + "\n\n"
                + "Описание: " + product.getDescription() + "\n\n"
                + product.getUrl();
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
        executeMessage(message);
    }

    private void showAllSelections(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("📼" + "Выберите подборку:");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for (Selection selection : selectionRepository.findAll()) {
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();
            var button = new InlineKeyboardButton();
            button.setText(selection.getName());
            button.setCallbackData(BUTTON_SELECTION);
            rowInLine.add(button);
            rowsInLine.add(rowInLine);
        }

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);

    }


    private void showAllGenres(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("📜" + "Выберите жанр:");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for (Genre genre : genreRepository.findAll()) {
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();
            var noButton = new InlineKeyboardButton();
            noButton.setText(genre.getName());
            noButton.setCallbackData(genre.getName());
            rowInLine.add(noButton);
            rowsInLine.add(rowInLine);
        }

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);

    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void showFilmsBySelections(long chatId, Long selectionId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("\uD83D\uDCFD" + "Выберите фильм:");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for (ProductDto product : productService.findProductsBySelection(selectionId)) {
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();
            var noButton = new InlineKeyboardButton();
            noButton.setText(product.getTitle());
            noButton.setCallbackData(product.getDescription());
            rowInLine.add(noButton);
            rowsInLine.add(rowInLine);
        }

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        executeMessage(message);

    }


}