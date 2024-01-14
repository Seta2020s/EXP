import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UserDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String[] userData = getUserData(scanner);
            saveUserData(userData);
            System.out.println("Данные успешно сохранены.");
        } catch (InvalidDataException | IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String[] getUserData(Scanner scanner) throws InvalidDataException {
        System.out.println("Введите данные (LastName FirstName Patronymic birthDate phoneNumber gender):");
        String input = scanner.nextLine();
        String[] userData = input.split("\\s+");

        if (userData.length != 6) {
            throw new InvalidDataException("Неверное количество данных. Введите все 6 значений.");
        }

        try {
            // Проверка формата даты
            new SimpleDateFormat("dd.MM.yyyy").parse(userData[3]);

            // Проверка формата номера телефона
            Long.parseLong(userData[4]);

            // Проверка пола
            if (!userData[5].matches("[fm]")) {
                throw new InvalidDataException("Неверное значение для пола. Используйте 'f' или 'm'.");
            }

            return userData;
        } catch (ParseException | NumberFormatException e) {
            throw new InvalidDataException("Ошибка формата данных: " + e.getMessage());
        }
    }

    private static void saveUserData(String[] userData) throws IOException {
        String fileName = userData[0] + ".txt";

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName, true), StandardCharsets.UTF_8))) {
            String formattedData = String.format("%s %s %s %s %s %s%n",
                    userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
            writer.write(formattedData);
        }
    }
}