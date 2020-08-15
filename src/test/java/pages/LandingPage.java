package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@data-role='accept-consent']")
    WebElement acceptConsentBtn;
    @FindBy(linkText = "Elektronika")
    WebElement electronicsCategory;
    @FindBy(linkText = "Komputery")
    WebElement computersCategory;

    public void acceptConsent() {
        if (acceptConsentBtn.isEnabled()) {
            acceptConsentBtn.click();
        } else throw new AssertionError("Couldn't accept consent");
    }

    public void chooseElectronicsCategory() {
        if (electronicsCategory.isEnabled()) {
            electronicsCategory.click();
        } else throw new AssertionError("Couldn't select Electronics category");
    }

    public void chooseComputersCategory() {
        if (computersCategory.isEnabled()) {
            computersCategory.click();
        } else throw new AssertionError("Couldn't select Computers category");
    }

}
