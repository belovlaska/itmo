package ru.itmo.prog.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.prog.models.Product;
import ru.itmo.prog.utils.LocalDateTimeAdapter;
import ru.itmo.prog.utils.ZonedDateTimeAdapter;
import ru.itmo.prog.utils.consoleShell.Console;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Класс для работы с файлом
 * @author belovlaska
 */
public class DumpController {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();

    private final Console consoleChanger;
    private final String fileName;

    public DumpController(String fileName, Console consoleChanger) {
        this.fileName = fileName;
        this.consoleChanger = consoleChanger;
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     */
    public void writeCollection(Collection<Product> collection) {
        try (BufferedWriter collectionBufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            collectionBufferedWriter.write(gson.toJson(collection));
            consoleChanger.println("Collection successfully saved to file!");
        } catch (IOException exception) {
            consoleChanger.printError("The file cannot be opened!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     *
     * @return Считанная коллекция
     */
    public Collection<Product> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var scanner = new Scanner(new File(fileName))) {
                var collectionType = new TypeToken<HashSet<Product>>() {}.getType();

                var jsonString = new StringBuilder();
                while(scanner.hasNextLine()){
                    var line = scanner.nextLine();
                    if (!line.equals("")) {
                          jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                HashSet<Product> collection = gson.fromJson(jsonString.toString(), collectionType);

                consoleChanger.println("Collection successfully loaded!");
                return collection;

            } catch (FileNotFoundException exception) {
                consoleChanger.printError("Boot file not found!");
            } catch (NoSuchElementException exception) {
                consoleChanger.printError("Boot file is empty!");
            } catch (JsonParseException exception) {
                consoleChanger.printError("The required collection was not found in the boot file!");
            } catch (IllegalStateException exception) {
                consoleChanger.printError("Unexpected error!");
                System.exit(0);
            }
        } else {
            consoleChanger.printError("Command line argument with boot file not found!");
        }
        return new HashSet<>();
    }
}
