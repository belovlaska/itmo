package client.input;

import client.utils.InputSteamer;
import client.utils.console.Console;
import common.domain.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.InvalidFormException;
import common.exceptions.InvalidValueException;
import common.exceptions.MustBeNotEmptyException;
import common.domain.*;
import common.exceptions.*;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

/**
 * Класс для ввода продукта
 * @author belovlaska
 */
public class ProductInput{

    private final long PRICE_LIMIT = 0;
    private final long HEIGHT_LIMIT = 0;
    private final Console console;

    public ProductInput(Console console) {
        this.console = console;
    }

    /**
     * Создает продукт
     */
    public Product make() throws IncorrectInputInScriptException, InvalidFormException, InvalidValueException {
        var product = new Product(
                1,
                inputName(),
                inputCoordinates(),
                LocalDateTime.now(),
                inputPrice(),
                inputUnitOfMeasureType(),
                inputPerson());
        if(!product.validate()) throw new InvalidFormException();
        return product;
    }

    /**
     * Ввод названия
     */
    private String inputName() throws IncorrectInputInScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Enter product name:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Name not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Name can't be blank!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }

        return name;
    }

    /**
     * Ввод координат
     */
    private Coordinates inputCoordinates() throws IncorrectInputInScriptException, InvalidFormException {
        Coordinates coordinates = new Coordinates(inputX(), inputY());
        if(!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    /**
     * Ввод y координаты
     */
    private long inputY() throws IncorrectInputInScriptException {
        long y;
        var fileMode = InputSteamer.getFileMode();
        while (true){
            try {
                console.println("Enter Y coordinate:");
                console.ps2();
                var strY = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strY);
                y = Long.parseLong(strY);
                break;
            }
            catch (NoSuchElementException exception){
                console.printError("Y coordinate not parsed!");
                if(fileMode) throw new IncorrectInputInScriptException();
            }
            catch (NumberFormatException exception){
                console.printError("Y coordinate must be integer!");
                if(fileMode) throw new IncorrectInputInScriptException();
            }
            catch (IllegalStateException exception){
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Ввод х координаты
     */
    private double inputX() throws IncorrectInputInScriptException {
        double x;
        var fileMode = InputSteamer.getFileMode();
        while (true){
            try {
                console.println("Enter X coordinate:");
                console.ps2();
                var strX = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strX);
                x = Double.parseDouble(strX);
                break;
            }
            catch (NoSuchElementException exception){
                console.printError("X coordinate not parsed!");
                if(fileMode) throw new IncorrectInputInScriptException();
            }
            catch (NumberFormatException exception){
                console.printError("X coordinate must be float number!");
                if(fileMode) throw new IncorrectInputInScriptException();
            }
            catch (IllegalStateException exception){
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Ввод цены
     */

    private Long inputPrice() throws IncorrectInputInScriptException {
        long price;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Enter product price (or null):");
                console.ps2();
                var strPrice = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);
                if(strPrice.equals("null") || strPrice.isEmpty()) {
                    return null;
                }
                price = Long.parseLong(strPrice);
                if (price <= PRICE_LIMIT) throw new InvalidValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Price not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Price must be integer!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (InvalidValueException exception) {
                console.printError("Price must be larger than 0!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return price;
    }

    /**
     * Ввод единиц измерения
     */
    private UnitOfMeasure inputUnitOfMeasureType() throws IncorrectInputInScriptException {
        var fileMode = InputSteamer.getFileMode();
        UnitOfMeasure unitOfMeasure;
        String strUnitOfMeasure;
        while (true){
            try {
                console.println("List of units of measure - " + UnitOfMeasure.names());
                console.println("Enter unit of measure: ");
                console.ps2();
                strUnitOfMeasure = InputSteamer.getScanner().nextLine().trim();
                if(fileMode) console.println(strUnitOfMeasure);
                if(strUnitOfMeasure.isEmpty()) throw new MustBeNotEmptyException();
                unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure.toUpperCase());
                break;
            }catch (NoSuchElementException exception) {
                console.printError("Unit of measure not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("There is no this unit of measure in list!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }catch (MustBeNotEmptyException exception) {
                console.printError("Unit of measure can't be blank!");
                if (fileMode) throw new IncorrectInputInScriptException();
            }catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return unitOfMeasure;
    }

    /**
     * Создание владельца
     */
    private Person inputPerson() throws IncorrectInputInScriptException, InvalidFormException {
        console.println("Enter no, if you don't want to create an owner. Enter yes, if you want to create an owner");
        while (true) {
            String inputStr = InputSteamer.getScanner().nextLine().trim();
            if (inputStr.equals("no"))
                return null;
            else if (inputStr.equals("yes")) {
                Person owner = new Person(
                        inputNameOfPerson(),
                        inputBirthday(),
                        inputHeight(),
                        inputEyeColor(),
                        inputHairColor());
                if (!owner.validate()) throw new InvalidFormException();
                return owner;
            }
            else
                console.println("Please, try again");
        }
    }

    /**
     * Ввод цвета волос
     */
    private Color inputHairColor() throws IncorrectInputInScriptException {
        var fileMode = InputSteamer.getFileMode();
        Color hairColor;
        while (true) {
            try {
                console.println("List of colors - " + Color.names());
                console.println("Enter hair color: ");
                console.ps2();
                var strHairColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strHairColor);
                if (strHairColor.isEmpty()) throw new MustBeNotEmptyException();
                hairColor = Color.valueOf(strHairColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Hair color not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("There is no this color in list!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Hair color can't be blank!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return hairColor;
    }

    /**
     * Ввод цвета глаз
     */
    private Color inputEyeColor() throws IncorrectInputInScriptException {
        var fileMode = InputSteamer.getFileMode();
        Color eyeColor;
        while (true) {
            try {
                console.println("List of colors - " + Color.names());
                console.println("Enter eye color (or null): ");
                console.ps2();
                var strEyeColor = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strEyeColor);
                if (strEyeColor.isEmpty() || strEyeColor.equals("null")) return null;
                eyeColor = Color.valueOf(strEyeColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Eye color not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                console.printError("There is no this color in list!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return eyeColor;
    }

    /**
     * Ввод роста
     */
    private long inputHeight() throws IncorrectInputInScriptException {
        long height;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Enter height:");
                console.ps2();
                var strPrice = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);
                height = Long.parseLong(strPrice);
                if (height <= HEIGHT_LIMIT) throw new InvalidValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Height not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Height must be integer!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (InvalidValueException exception) {
                console.printError("Height must be larger than 0!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return height;
    }

    /**
     * Ввод дня рождения
     */
    private ZonedDateTime inputBirthday() throws IncorrectInputInScriptException {
        return new ZonedDateTimeForm(console).adapt();
    }

    /**
     * Ввод имени владельца
     */
    private String inputNameOfPerson() throws IncorrectInputInScriptException {
        String name;
        var fileMode = InputSteamer.getFileMode();
        while (true) {
            try {
                console.println("Enter owner's name:");
                console.ps2();

                name = InputSteamer.getScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Name not parsed!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("name can't be blank!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Unexpected error!");
                System.exit(0);
            }
        }

        return name;
    }
}


