import com.pengrad.telegrambot.TelegramBot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BirthdayScheduler {
    private final ScheduledExecutorService scheduler;
    private final TelegramBot telegramBot;

    public BirthdayScheduler(TelegramBot telegramBot) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.telegramBot = telegramBot;
    }

    public void start() {
        scheduleDailyCheck(9, 0);
    }

    private void scheduleDailyCheck(int hour, int minute) {
        LocalTime targetTime = LocalTime.of(hour, minute);
        LocalTime now = LocalTime.now();

        long initialDelay;
        if (now.isBefore(targetTime)) {
            initialDelay = Duration.between(now, targetTime).toMinutes();
        } else {
            initialDelay = Duration.between(now, targetTime.plusHours(24)).toMinutes();
        }

        scheduler.scheduleAtFixedRate(() -> {
            LocalDate today = LocalDate.now();
            checkBirthdays(today);  // передаем параметр
        }, initialDelay,
                24 * 60,
                TimeUnit.MINUTES);

        System.out.println("Birthday scheduler started. First check in " + initialDelay + " minutes");
    }

    private void checkBirthdays(LocalDate userBD) {
        try {
            LocalDate today = LocalDate.now();
            System.out.println("Checking birthdays for date: " + today);

            if (userBD.equals(today)) {
                String message = "🎉 Сегодня день рождения! Поздравляем!";
            }

        } catch (Exception e) {
            System.err.println("Error in birthday check: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}