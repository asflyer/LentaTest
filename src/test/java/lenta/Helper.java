package lenta;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper extends NavigatorHelper
{

    public void checkShoppingList(ArrayList addingList)
    {
        //Список покупок
        //driver.findElement(By.cssSelector("[class=\"header__cart-text\"]")).click(); - полная версия
        driver.findElement(By.cssSelector("[class=\"header__cart\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[class=\"shopping-list__title\"]"))));
        ArrayList stringList = new ArrayList<String>();
        List<WebElement> listElement =  driver.findElements(By.cssSelector("[class=\"sku-card-shopping-list__title\"]"));
        for (WebElement element :listElement)
        {
            String textElement = element.getAttribute("innerHTML");
            stringList.add(textElement);
        }
        Collections.sort(stringList); //Список покупок (отображаемый)
        Collections.sort(addingList); //Список покупок (добавляемые товары)

        //Демонстрационный вывод
        //System.out.println(stringList);
        //System.out.println(addingList);

        Assert.assertEquals(addingList, stringList);
    }

    public ArrayList addingGoodsToShoppingCart(ArrayList addingList, int N)
    {
        int i=7;
        int firstItem = i;
        // i должно быть больше 3 потому что в первой и второй ячейке может быть баннер
        //отсчет i начинается с 0 от первой ячейки сетки. То есть 0-1 и 4-5 могут быть банерами
        do {
            int j = 0;
            WebElement skus = driver.findElement(By.cssSelector("[class=\"catalog__skus-container\"]"));
            try
            {
                skus.findElement(By.xpath((".//div[1]/div[" + i + "]")));
                j=1;
            }
            catch (NoSuchElementException e)
            {
                jse.executeScript("window.scrollBy(0,-100)", ""); //скролл}
                driver.findElement(By.id("catalog_load_more")).click();
            }
            if(j==1)
            {
                WebElement item = skus.findElement(By.xpath((".//div[1]//div[" + i + "]")));

                jse.executeScript("arguments[0].scrollIntoView();", item);
                item.findElement(By.cssSelector("button[type=\"button\"]")).click();
                wait.until(ExpectedConditions.visibilityOf(item.findElement(By.tagName("span"))));
                String tv, tv1;
                tv = item.findElement(By.cssSelector("[class=\"sku-card-small__title\"]")).getAttribute("innerHTML");//название товара
                tv1 = item.findElement(By.cssSelector("[class=\"card-item-counter__control-container\"]")).findElement(By.tagName("span")).getAttribute("innerHTML");
                //Демонстрационный вывод в консоль
                System.out.println(i-firstItem+1 + ") " + tv + " - " + tv1); //вывод в консоль названия и анимации "Товар добавлен"
                if(i-firstItem+1 < 101)
                {
                    Assert.assertEquals(tv1,"Товар добавлен");
                    addingList.add(tv);
                }
                else
                {
                    Assert.assertEquals(tv1,"Слишком много товаров");
                }
                i++;
            }

        }
        while (i<N+firstItem);
        return addingList;
    }


    public void shoppingListToText()
    {
        String ttt = driver.findElement(By.cssSelector(("[class=\"shopping-list__sku-list\"]"))).getText();
        System.out.println("Список покупок: \n" + ttt);
    }




    public void getShoppingListLink() throws IOException, UnsupportedFlavorException
    {
        driver.findElement(By.cssSelector("[class=\"sharing-control__icon\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"sharing-option sharing-option--copy\"]")));
        WebElement lin = driver.findElement(By.cssSelector("[class=\"sharing-option sharing-option--copy\"]"));
        jse.executeScript("arguments[0].scrollIntoView();", lin);
        lin.click();
        String link = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        //текст из буфера обмена
        System.out.println(link);
    }

    public void LogIn ()
    {
        String loginValue = "";
        String passwordValue = "";
        if (env == 1)
        {
            loginValue = "79095788078";
            passwordValue = "alistan747";
        }
        else if (env == 2)
        {
            loginValue = "epiadmin";
            passwordValue = "Password1234!";
        }

        Dimension size = driver.manage().window().getSize();
        System.out.println(size);
        if (size.width < 1000)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__bar-wrap\"]")));
            driver.findElement(By.cssSelector("[class=\"header__bar-wrap\"]")).findElement(By.xpath("a[4]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__profile-menu-inner\"]")));
            driver.findElement(By.cssSelector("[class=\"header__profile-menu-inner\"]")).findElement(By.xpath("a[1]")).click();
        }
        else
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__personal-cabinet-text\"]")));
            driver.findElement(By.cssSelector("[class=\"header__personal-cabinet-text\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__profile-menu-inner\"]")));
            driver.findElement(By.cssSelector("[class=\"header__profile-menu-inner\"]")).findElement(By.tagName("svg")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login-app__title")));
        WebElement login = driver.findElement(By.name("login"));
        login.findElement(By.cssSelector("input[type=\"text\"]")).sendKeys(loginValue);
        login.findElement(By.cssSelector("input[type=\"password\"]")).sendKeys(passwordValue);
        login.findElement(By.cssSelector("button[type=\"submit\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__logo\"]")));
    }

    public void clearShoppingList()
    {

        driver.findElement(By.cssSelector("[class=\"header__cart-text\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[class=\"shopping-list__title\"]"))));
        try
        {
            driver.findElement(By.cssSelector("[class=\"shopping-list__notified-message\"]"));
        }
        catch (NoSuchElementException e)
        {
            driver.findElement(By.cssSelector("[class=\"shopping-list__clear-button\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"popup__title\"]")));
            driver.findElement(By.cssSelector("[class=\"button button--small button--primary clear-list-popup__control-button\"]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[class=\"shopping-list__title\"]"))));
        }
        String emptyText = driver.findElement(By.cssSelector("[class=\"shopping-list__notified-message\"]")).getAttribute("innerHTML");
        Assert.assertEquals(emptyText, "Тут пока пусто, вы можете добавить сюда товары из каталога");
    }

}
