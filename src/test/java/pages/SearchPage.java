package pages;

import Models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {

    private WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(partialLinkText = "Części do laptop")
    WebElement laptopPartsCategory;
    @FindBy(xpath = "//a[@class='_w7z6o _uj8z7 _1h7wt _1bo4a _6kfrx'][contains(text(),'RAM')]")
    WebElement RAMCategory;
    @FindBy(name = "string")
    WebElement searchBar;
    @FindBy(xpath = "//button[@data-role='search-button']")
    WebElement searchBtn;
    @FindBy(className = "_9c44d_2H7Kt")
    List<WebElement> allProductsFromCurrentPage;
    @FindBy(xpath = "//span[contains(text(),'nowe')]")
    WebElement newStateOption;
    @FindBy(xpath = "//div[@class='_1bo4a _np6in _ku8d6 _3db39_14wVQ _kiiea']")
    WebElement numberOfPages;
    @FindBy(xpath = "//div[@class='_1bo4a _np6in _ku8d6 _3db39_14wVQ _kiiea']//i[@class='_nem5f _1av8u']")
    WebElement goToNextPageButton;
    @FindBy(xpath = "//span[contains(text(),'16 GB')]")
    WebElement size16GBOption;
    @FindBy(xpath = "//select[@class='_1h7wt _k70df _7qjq4 _27496_3VqWr']")
    WebElement sortingDropdown;

    public void chooseLaptopPartsCategory() {
        if (laptopPartsCategory.isEnabled()) {
            laptopPartsCategory.click();
        } else throw new AssertionError("Couldn't select Laptop Parts category");
    }

    public void chooseRAMCategory() {
        if (RAMCategory.isEnabled()) {
            RAMCategory.click();
        } else throw new AssertionError("Couldn't select RAM category");
    }

    public void searchProduct(String product) {
        if (searchBar.isDisplayed()) {
            searchBar.clear();
            searchBar.sendKeys(product);
        } else throw new AssertionError("Couldn't type search query");

        if (searchBtn.isEnabled()) {
            searchBtn.click();
        } else throw new AssertionError("Couldn't click on search button");
    }

    public void chooseNewState() {
        if (!newStateOption.isSelected()) {
            newStateOption.click();
        }
    }

    public List<Product> getProductsFromCurrentPage() {
        List<Product> products = new ArrayList<Product>();
        for (WebElement product : allProductsFromCurrentPage) {
            String currentProductName = product.findElement(By.className("_9c44d_LUA1k")).getText();
            String priceOfCurrentProduct = product.findElement(By.className("_9c44d_1zemI")).getText();

            products.add(new Product(currentProductName, parseStringPriceToDouble(priceOfCurrentProduct)));
        }
        return products;
    }

    public int getNumberOfPages() {
        return Integer.parseInt(numberOfPages.getText().replaceAll("z\n", ""));
    }

    public void gotToNextPage() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(goToNextPageButton).click().build().perform();
        if (goToNextPageButton.isEnabled()) {
            goToNextPageButton.click();
            Thread.sleep(500);
        }
    }

    public void choose16GBSize() throws InterruptedException {
        if (!size16GBOption.isSelected()) {
            size16GBOption.click();
            Thread.sleep(500);
        }
    }

    private double parseStringPriceToDouble(String price) {
        String priceStripCurrency = price.replaceAll(" zł", "");
        String priceChangeCommaToPeriod = priceStripCurrency.replaceAll(",", ".");
        String priceStripSpaces = priceChangeCommaToPeriod.replaceAll(" ", "");
        return Double.parseDouble(priceStripSpaces);
    }

    public void sortPriceAsc(){
        Select priceAsc = new Select(sortingDropdown);
        priceAsc.selectByValue("p");
    }
}
