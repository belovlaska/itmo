package client.input;

import client.utils.InputSteamer;
import client.utils.console.Console;
import common.exceptions.IncorrectInputInScriptException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Класс для ввода даты рождения
 * @author belovlaska
 *
 */

public class ZonedDateTimeForm {

    private final Console console;
    public ZonedDateTimeForm(Console console) {
        this.console = console;
    }
    public ZonedDateTime adapt() throws IncorrectInputInScriptException {
        var fileMode = InputSteamer.getFileMode();
        ZonedDateTime zonedDateTime;
        while (true) {
            try {
                console.println("Enter date and time of owner's birthday(or null) in format dd.mm.yyyy hh:mm:ss VV\n" +
                        "Where dd - day, mm - month, yyyy - year, hh - hours, mm - minutes, ss - seconds и VV - region/town\n" +
                        "Example: 29.06.2017 19:07:34 Asia/Dubai");
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
                console.printError("Date and time in incorrect format!");
                if(fileMode) throw new IncorrectInputInScriptException();
            }catch (NoSuchElementException exception) {
                console.printError("Date and time not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return zonedDateTime;
    }
}
