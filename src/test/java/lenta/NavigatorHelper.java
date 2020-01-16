package lenta;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class NavigatorHelper extends Web
{



    public void openCategory(String category)
    {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href=\"/catalog/\"]"))); //не работает
        //добавил доп проверку, удалить, если еще раз упадет

        driver.findElement(By.cssSelector("[class=\"header__catalog\"]")).click();
        //driver.findElement(By.partialLinkText("Каталог товаров")).click();

        try
        {
            driver.findElement(By.cssSelector("[class=\"popup__close\"]")).click();
        } catch (Exception e) {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(category)));
        driver.findElement(By.partialLinkText(category)).click();
    }

    public void openCrazy()
    {

    }

    public void firstCityPick(String town)
    {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(town)));
        driver.findElement(By.linkText(town)).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(town)));
        //Закомментил потому что в Моб версии нет города
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"header__logo\"]")));
    }
}
