package lenta;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Collections;
import java.util.List;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
Для запуска параллельно:
открываем папку D:\testtest
Там вводим в адресную строку cmd
В консоли вводим
mvn clean test -Dtest=FirstTest
 */


public class FirstTest extends Helper
{
        /*
        ДОДЕЛАТЬ !!!
        Для различных размеров окна разные варианты функц
        добавить класс Model куда запихнуть модель "товар" со свойствами цена, название
         */

    @Test
    public void CookieChecksTest()   //имя метода с маленькой, имя Класса с большой
    {
        String town = "Калуга";
        int j=0;
        String cookie = "";
        do
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(town)));
            driver.findElement(By.linkText(town)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"cookie-usage-notice__inner\"]")));
            if (j==0)
            {
                cookie = driver.findElement(By.cssSelector("[class=\"cookie-usage-notice__text-inner cookie-usage-notice__text-inner--desktop\"]")).getText();
            }
            else if (j==1)
            {
                cookie = driver.findElement(By.cssSelector("[class=\"cookie-usage-notice__text-inner cookie-usage-notice__text-inner--mobile\"]")).getText();
            }
            Assert.assertEquals(cookie, "Мы используем cookie, чтобы сайт был лучше");
            //System.out.println("Отображаемый текст в cookie = \"" + cookie + "\"");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"cookie-usage-notice__link\"]")));
            driver.findElement(By.cssSelector("[class=\"cookie-usage-notice__link\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"popup popup--cookie-usage-info popup--show\"]")));
            WebElement popupCookie = driver.findElement(By.cssSelector("[class=\"popup popup--cookie-usage-info popup--show\"]"));
            String text = popupCookie.findElement((By.cssSelector("[class=\"cookie-usage-info-popup__text\"]"))).getAttribute("innerHTML");
            //System.out.println(text);
            Assert.assertEquals(text, "<p style=\"text-align: justify;\">Используя настоящий сайт, Вы даете согласие с&nbsp;<a title=\"политикой обработки персональных данных и списком контрагентов\" href=\"/link/553b004449ce41b48a110dbebc7a3fc7.aspx\">политикой обработки персональных данных и списком контрагентов,</a> на обработку cookie (данных, получаемых с сайтов и хранящихся на Вашем устройстве), а также сведений о Вашем местоположении, типе и версии Вашего устройства, его операционной системы, используемом браузере, источнике, откуда Вы пришли на настоящий сайт, Вашем ip-адресе, истории активностей на настоящем сайте, персональных предпочтениях и интересах. Указанная информация обрабатывается в целях функционирования настоящего сайта, идентификации пользователя, аналитических и информационных целей, проведения исследований, рекламы. Покиньте сайт, если Вы не согласны с настоящими условиями.</p>");

            popupCookie.findElement(By.cssSelector("button[type=\"button\"]")).click();

            if(j==0)
            {
                //Теперь запускаем мобильную версию и прогоняем еще раз
                driver.quit();
                startMobileDriver();
                j++;
            }
            else if (j == 1)
            {
                j++;
            }
        }while (j<2);
    }



    @Test
    public void addingSomeGoodsTest() throws IOException, UnsupportedFlavorException    //имя метода с маленькой, имя Класса с большой
    {
        int N = 13; //Количество добавляемых товаров
        ArrayList addingList = new ArrayList<String>(N); //Список покупок
        //String town = "Курган";
        String town = "Калуга";
        //String category = "Кондитерские изделия";
        String category = "Чай, кофе, какао";

        firstCityPick(town);
        openCategory(category);
        addingGoodsToShoppingCart(addingList, N);
        //Демонстрационный метод
        //shoppingListToText();
        checkShoppingList(addingList);
        getShoppingListLink();
    }

@Test public void addingGoodsToLogInUser() throws IOException, UnsupportedFlavorException
{
    /*
    Добавление товаров авторизованному пользователю. Авторизация, добавление товаров,
    Открытие мобильной версии, проверка, что товары были добавлены в корзину юзера
     */
    String town = "Калуга";
    int N = 5; //Количество добавляемых товаров
    ArrayList addingList = new ArrayList<String>(N); //Список покупок
    String category = "Чай, кофе, какао";

    firstCityPick(town);
    LogIn ();
    clearShoppingList();
    openCategory(category);
    addingGoodsToShoppingCart(addingList, N);
    driver.quit();
    //запустить мобилу
    startMobileDriver();
    firstCityPick(town);
    LogIn ();
    checkShoppingList(addingList);
    getShoppingListLink();
}




}
