package ru.itmo.prog.utils;

import ru.itmo.prog.exceptions.IncorrectScriptException;
import ru.itmo.prog.exceptions.MustBeNotEmptyException;
import ru.itmo.prog.utils.consoleShell.Console;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class ZonedDateTimeAdapter {

    private final Console console;
    public ZonedDateTimeAdapter(Console console) {
        this.console = console;
    }
    public ZonedDateTime adapt() throws IncorrectScriptException {
        var fileMode = InputSteamer.getFileMode();
        ZonedDateTime zonedDateTime;
        while (true) {
            try {
                console.println("Введите дату и время рождения владельца(или null) в формате dd.mm.yyyy hh:mm:ss VV\n" +
                        "Где dd - день, mm - месяц, yyyy - год, hh - часы, mm - минуты, ss - секунды и VV - регион/город\n" +
                        "Пример: 29.06.2017 19:07:34 Asia/Dubai");
                console.ps2();

                var strZonedDateTime = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strZonedDateTime);
                if (strZonedDateTime.equals("null") || strZonedDateTime.isEmpty()) return null;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                        .ofPattern("dd.MM.yyyy HH:mm:ss VV");

                // 2. parse date/time in String to default ZonedDateTime format
                zonedDateTime = ZonedDateTime.parse(strZonedDateTime, dateTimeFormatter);
                break;
            } catch (DateTimeParseException exception) {
                console.printError("Дата и время введены в неправильном формате!");
                if(fileMode) throw new IncorrectScriptException();
            }catch (NoSuchElementException exception) {
                console.printError("Дата и время не распознаны!");
                if (fileMode) throw new IncorrectScriptException();
            }catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return zonedDateTime;
    }
}
