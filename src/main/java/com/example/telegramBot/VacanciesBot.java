package com.example.telegramBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacanciesBot extends TelegramLongPollingBot {
    public VacanciesBot() {
        super("6464262432:AAFYtel6XbCdStt9khrCObQfsUS3KuWau0g");
    }

    @Override
    public void onUpdateReceived(Update update) {
      try {
          if (update.getMessage() !=null){
              handleStartCommand(update);
          }
          if (update.getCallbackQuery() != null){
              String callbackData = update.getCallbackQuery().getData();

              if ("showJuniorVacancies".equals(callbackData)){
                  showJuniorVacancies(update);
              }
              else if ("showMiddleVacancies".equals(callbackData)){
                  showMiddleVacancies(update);
              }
              else if ("showSeniorVacancies".equals(callbackData)){
                  showSeniorVacancies(update);
              }else if (callbackData.startsWith("vacancyId=")){
                  String id = callbackData.split("=")[1];
                  showVacanciesDescruption(id, update);
              }

          }
      }catch (Exception e){
          throw new RuntimeException("Cant send massage to user!",e);
      }

    }

    private void showSeniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getSeniorVacanciesMenu());
        execute(sendMessage);
    }

    private void showMiddleVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getMiddleVacanciesMenu());
        execute(sendMessage);
    }
    private void showJuniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getJuniorVacanciesMenu());
        execute(sendMessage);

    }

    private void showVacanciesDescruption(String id, Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Vacancy description for vacancy with id = "+ id);
        execute(sendMessage);
    }

    private ReplyKeyboard getJuniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton meVacancy = new InlineKeyboardButton();
        meVacancy.setText("Junior Java developer at MA");
        meVacancy.setCallbackData("vacancyId=1");
        row.add(meVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Junior Dev at Google");
        googleVacancy.setCallbackData("vacancyId=2");
        row.add(googleVacancy);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));
        return keyboard;

    }
    private ReplyKeyboard getMiddleVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton meVacancy = new InlineKeyboardButton();
        meVacancy.setText("Middle Java developer at MA");
        meVacancy.setCallbackData("vacancyId=1");
        row.add(meVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Middle Dev at Google");
        googleVacancy.setCallbackData("vacancyId=2");
        row.add(googleVacancy);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));
        return keyboard;
    }
    private ReplyKeyboard getSeniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton meVacancy = new InlineKeyboardButton();
        meVacancy.setText("Senior Java developer at MA");
        meVacancy.setCallbackData("vacancyId=1");
        row.add(meVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Senior Dev at Google");
        googleVacancy.setCallbackData("vacancyId=2");
        row.add(googleVacancy);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));
        return keyboard;
    }

    private void handleStartCommand(Update update){
        String text = update.getMessage().getText();
        System.out.println("received text is " + text);
        SendMessage sendMassage = new SendMessage();
        sendMassage.setChatId(update.getMessage().getChatId());
        sendMassage.setText("Welcome to bot, choose what u need:");
        sendMassage.setReplyMarkup(getStartMenu());
        try {
            execute(sendMassage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard getStartMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton junior = new InlineKeyboardButton();
        junior.setText("Junior");
        junior.setCallbackData("showJuniorVacancies");
        row.add(junior);

        InlineKeyboardButton middle = new InlineKeyboardButton();
        middle.setText("Middle");
        middle.setCallbackData("showMiddleVacancies");
        row.add(middle);

        InlineKeyboardButton senior = new InlineKeyboardButton();
        senior.setText("Senior");
        senior.setCallbackData("showSeniorVacancies");
        row.add(senior);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));
        return keyboard;
    }

    @Override
    public String getBotUsername() {
        return "StudyVacancies_bot";
    }
}
