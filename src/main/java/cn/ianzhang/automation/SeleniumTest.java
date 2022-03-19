package cn.ianzhang.automation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumTest {

    @Test
    public void test() {
        System.setProperty("webdriver.chrome.driver", "E:\\liyao\\src\\test\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://cn.bing.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@aria-label='搜索网页']/../input")).sendKeys("Bing");
        driver.findElement(By.xpath("//label[@aria-label='搜索网页']/child::*")).click();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
        driver.findElement(By.xpath("//a[@aria-label='第 2 页']")).click();
        Map<String, Integer> map = new HashMap<>();
        String[] titleArr = new String[3];
        String[] urlArr = new String[3];
        String[] daminArr = new String[3];
        for (int i = 1; i <= 3; i++) {
            WebElement ele = driver.findElement(By.xpath(String.format("((//main//li)[%s]//a)[2]", i)));
            String url = ele.getAttribute("href");
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            matcher.find();
            //存储title数组
            titleArr[i - 1] = ele.getText();
            //存储url数组
            urlArr[i - 1] = url;
            //存储顶级域名数组
            daminArr[i - 1] = matcher.group();
            if (map.get(daminArr[i - 1]) != null) {
                map.put(daminArr[i - 1], map.get(daminArr[i - 1]) + 1);
            } else {
                map.put(daminArr[i - 1], 1);
            }
        }
        System.out.println("\n结果列表：");
        for (int i = 0; i < 3; i++) {
            System.out.println(titleArr[i] + "  --> " + urlArr[i]);
        }
        //得到map中所有的键
        Set<String> keyset = map.keySet();
        //创建set集合的迭代器
        Iterator<String> it = keyset.iterator();
        System.out.println("\n结果统计：");
        while (it.hasNext()) {
            String key = it.next();
            Integer value = map.get(key);
            System.out.println(key + "  --> " + value);
        }
        driver.close();
    }
}
