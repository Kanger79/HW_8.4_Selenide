package ru.netology.domain;

import com.codeborne.selenide.Condition;
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

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardWithDeliveryTest {
    private WebDriver driver;

    double random = Math.random() * 28; //генерация случайного числа от 0 до 27,9999
    int rnd = (int) random + 3;  //выделение целого числа из случайного и смещение диаппазона на 3 (от 3 до 30)

    @BeforeEach
        void setUp() {
        driver = new ChromeDriver();
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    private String genDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void shouldValidTest() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

        System.out.println(rnd); //для визуального контроля метода генерации случайного числа
    }

    @Test
    public void shouldTestNoCity() {
        //   $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));


        System.out.println(rnd);
    }

    @Test
    public void shouldTestNoDate() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        // $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смирнов Николай");
        $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"), Duration.ofSeconds(15));

    }

    @Test
    public void shouldTestNoName() {
        $("[data-test-id='city'] input").setValue("Краснодар");
        String currentDate = genDate(rnd, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
       // $("[data-test-id='name'] input").setValue("Смирнов Николай");
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
        // $("[data-test-id='phone'] input").setValue(("+78002008002"));
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }

}
