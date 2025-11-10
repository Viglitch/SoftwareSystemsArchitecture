import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class Bot {
    public static void start(String botToken) {
        TelegramBot bot = new TelegramBot(botToken);
        bot.setUpdatesListener(updates -> {
            for (Update update: updates) {
                if (update.message() != null && update.message().text() != null) {
                    Long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    String userName = update.message().chat().firstName();

                    sendMessage(bot, chatId, "Привет, " + userName + "!\n"
                            + "Я ваш бот и я умею поздравлять с днем рождения.\n"
                            + "Как мной пользоваться: \n"
                            + "/newBirthday - добавить день рождения в базу\n"
                            + "/allBirthdays - посмотреть все дни рождения в базе\n"
                            + "/deleteBirthday - удалить день рождения из базы\n");

                    switch (messageText) {
                        case "/newBirthday":
                            sendMessage(bot, chatId, "Кого поздравляем? (введите имя)");
                            String nameText = update.message().text();
                            sendMessage(bot, chatId, "Когда поздравляем? (дата рождения вида DD.MM.YYYY)");
                            String dateText = update.message().text();
                            sendMessage(bot, chatId, "Поздравлю "+nameText+" в "+dateText);
                            break;
                        case "/allBirthdays":
                            sendMessage(bot, chatId, "Пока нет дней рождения");
                            break;
                        case "/deleteBirthday":
                            sendMessage(bot, chatId, "Пока нет дней рождения для удаления");
                            break;
                        default:
                            sendMessage(bot, chatId, "Неизвестная команда: " + messageText);
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
