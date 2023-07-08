package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardWithDeliveryTest {

    double random = Math.random() * 28; //генерация случайного числа от 0 до 27,9999
    int rnd = (int) random + 3;  //выделение целого числа из случайного и смещение диаппазона на 3 (от 3 до 30)

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String genDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void shouldValidTest() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }


    @Test
    public void shouldTestNoCity() {
        $("[data-test-id='city'] input").setValue("");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

        System.out.println(rnd); //для визуального контроля метода генерации случайного числа
    }

    @Test
    public void shouldTestNoDate() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"), Duration.ofSeconds(15));

        System.out.println(rnd); //для визуального контроля метода генерации случайного числа

    }

    @Test
    public void shouldTestNoName() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }

    @Test
    public void shouldTestNoPhone() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue((""));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldTestNoCheckBox() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));

        $("button.button").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe().isDisplayed();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible, Duration.ofSeconds(15)).isSelected();
    }

    @Test
    public void shouldTestInvalidCity() {
        $("[data-test-id='city'] input").setValue("Сызрань");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Иванов Никита");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldTestInvalidDate() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldTestInvalidName() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("СмирнOFF Maxim");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(15));

    }

    @Test
    public void shouldTestInvalidPhone() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+7800200800200"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }

    @Test
    public void shouldValidTestFindInMassive() {       //задание №2 - поиск в массиве
        $("[data-test-id='city'] input").setValue("Кр");
        $$(".menu-item__control").findBy(text("Краснодар")).click();
        String currentDate = genDate(8, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $$(".calendar__day").findBy(text("16")).click();
        $("[data-test-id='name'] input").setValue("Паровозов Аркадий");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

    @Test
    public void shouldValidTestMovingArrows() {       //задание №2 - выбор стрелками
        $("[data-test-id='city'] input").setValue("Кр");
        actions().moveToElement($("[data-test-id='city'] input")).perform();
        $("[data-test-id='city'] input").sendKeys(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER);
        String currentDate = genDate(7, "dd.MM.yyyy");
        actions().moveToElement($("[data-test-id='date'] input")).perform();
        $("[data-test-id='date'] input").sendKeys(Keys.DOWN, Keys.DOWN, Keys.LEFT, Keys.LEFT, Keys.LEFT);
        $("[data-test-id='name'] input").setValue("Петров Владимир");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }

}
