package ru.netology.cardorder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestCardOrder {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendFormWithValidData() {
        $("[data-test-id='name'] .input__control").setValue("Сергей Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79031112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='order-success']").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendFormWithEmptyName() {
        $("[data-test-id='name'] .input__control").setValue("");
        $("[data-test-id='phone'] .input__control").setValue("+79031112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithNotCyrillicName() {
        $("[data-test-id='name'] .input__control").setValue("Dmitry");
        $("[data-test-id='phone'] .input__control").setValue("+79031112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormWithNumbersInNameField() {
        $("[data-test-id='name'] .input__control").setValue("123456");
        $("[data-test-id='phone'] .input__control").setValue("+79031112233");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormWithInvalidPhoneNumber() {
        $("[data-test-id='name'] .input__control").setValue("Ирина Петрова");
        $("[data-test-id='phone'] .input__control").setValue("+790311122334");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormWithLettersInPhoneNumberField() {
        $("[data-test-id='name'] .input__control").setValue("Ирина Петрова");
        $("[data-test-id='phone'] .input__control").setValue("abcde");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormWithEmptyPhoneNumber() {
        $("[data-test-id='name'] .input__control").setValue("Юлия Нгуен");
        $("[data-test-id='phone'] .input__control").setValue("");
        $("[class='checkbox__box']").click();
        $("[class='button__text']").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithNotCheckedAgreement() {
        $("[data-test-id='name'] .input__control").setValue("Сергей Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79031112233");
        $("[class='button__text']").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
