package lenta;
import org.junit.After;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Before;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Web
{


    public ChromeDriver driver;
    public JavascriptExecutor jse;
    public WebDriverWait wait;
    public File scrFile;

    int implicitlyWait = 15;
    public String site;

    int env = 1;// 1 = "prod";
    // 2 = "stage";

    @Before
    public void setUp (){
        if (env == 1)
        {
            site = "lenta";
            System.out.println("TestOnProd");
        }
        else if (env == 2)
        {
            site = "stage.lentatest";
            System.out.println("TestOnStage");
        }



        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\Drivers\\chromedriver79");

        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        driver.get("https://" + site + ".com/"); //открытие браузера
        jse = (JavascriptExecutor)driver; //Для скороллов
        wait = new WebDriverWait(driver, 15);

        scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

    }

    public void startMobileDriver()
    {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(375, 667)); //iPhone 6/7/8
        driver.get("https://" + site + ".com/"); //открытие браузера
        wait = new WebDriverWait(driver, 15);
    }

    @After
    public void close()
    {
        driver.quit();
    }


}
