package server.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import common.domain.Product;
import server.App;

import server.utils.LocalDateTimeAdapter;
import server.utils.ZonedDateTimeAdapter;


import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Класс для работы с файлом
 * @author belovlaska
 */
public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();


    private final String fileName;

    public DumpManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     */
    public void writeCollection(Collection<Product> collection) {
        try (BufferedWriter collectionBufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            collectionBufferedWriter.write(gson.toJson(collection));
            App.logger.info("Collection successfully saved to file!");
        } catch (IOException exception) {
            App.logger.info("The file cannot be opened!");
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

                App.logger.info("Collection successfully loaded!");
                return collection;

            } catch (FileNotFoundException exception) {
                App.logger.info("Boot file not found!");
            } catch (NoSuchElementException exception) {
                App.logger.info("Boot file is empty!");
            } catch (JsonParseException exception) {
                App.logger.info("The required collection was not found in the boot file!");
            } catch (IllegalStateException exception) {
                App.logger.info("Unexpected error!");
                System.exit(0);
            }
        } else {
            App.logger.info("Command line argument with boot file not found!");
        }
        return new HashSet<>();
    }
}
