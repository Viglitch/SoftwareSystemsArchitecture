import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class Bot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("BOT_TOKEN");
        bot.setUpdatesListener(updates -> {
            for (Update update: updates) {
                if (update.message() != null && update.message().text() != null) {
                    Long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    String userName = update.message().chat().firstName();

                    switch (messageText) {
                        case "/start":
                            sendMessage(bot, chatId, "Привет, " + userName + "! Я простой бот.");
                            break;
                        case "/help":
                            sendMessage(bot, chatId, "Доступные команды:\n/start - начать\n/help - помощь");
                            break;
                        default:
                            sendMessage(bot, chatId, "Вы сказали: " + messageText);
                            break;
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static void sendMessage(TelegramBot bot, Long chatId, String text) {
        SendMessage request = new SendMessage(chatId, text);
        SendResponse response = bot.execute(request);

        if (!response.isOk()) {
            System.out.println("Ошибка отправки сообщения: " + response.description());
        }
    }
}
