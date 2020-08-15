import Models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LandingPage;
import pages.SearchPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchRAM {

    private static WebDriver driver;
    private static LandingPage landingPage;
    private static SearchPage searchPage;
    private static final String PRODUCT = "ddr4 sodimm 2666";
    private static List<Product> productsFromCurrentPage;
    private static List<Product> allProducts;


    public static void main(String[] args) throws InterruptedException {
        printWelcome();
        setUp();
        searchRAM();
        List<Product> products = getAllProducts();
        printAllProducts(products);
        System.out.println("The average price from parsed products is " + findAveragePrice(products));
        tearDown();
    }

    private static void printAllProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.getName() + " " + product.getPrice());
        }
    }

    private static double findAveragePrice(List<Product> products){
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return Math.floor(sum / products.size());
    }

    private static void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.setHeadless(true);

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://allegro.pl/");
    }

    private static void searchRAM() throws InterruptedException {
        landingPage = new LandingPage(driver);
        searchPage = new SearchPage(driver);

        landingPage.acceptConsent();
        landingPage.chooseElectronicsCategory();
        landingPage.chooseComputersCategory();
        Thread.sleep(300);

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        searchPage.chooseLaptopPartsCategory();
        searchPage.chooseRAMCategory();
        searchPage.searchProduct(PRODUCT);
        searchPage.chooseNewState();
        searchPage.choose16GBSize();
        searchPage.sortPriceAsc();
        Thread.sleep(1000);
    }

    private static List<Product> getAllProducts() throws InterruptedException {
        allProducts = new ArrayList<Product>();

        for (int i = 0; i <searchPage.getNumberOfPages(); i++) {
            System.out.println("Parsing page number " + (i+1) + "....");
            productsFromCurrentPage = searchPage.getProductsFromCurrentPage();
            allProducts.addAll(productsFromCurrentPage);
            if (i == searchPage.getNumberOfPages()-1){
                break;
            }
            else searchPage.gotToNextPage();
        }
        return allProducts;
    }

    private static void tearDown(){
        driver.quit();
    }

    private static void printWelcome(){
        System.out.println("The script will find the 16 GB RAMs on Allegro website");
        System.out.println("The script is starting...");
        System.out.println("*************************");
    }

}
