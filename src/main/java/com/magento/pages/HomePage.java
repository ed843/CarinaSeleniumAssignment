package com.magento.pages;


import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {
    @FindBy(css = ".logo")
    private ExtendedWebElement logo;

    @FindBy(css = "#search")
    private ExtendedWebElement searchInput;

    @FindBy(css = ".authorization-link a")
    private ExtendedWebElement loginLink;

    @FindBy(css = ".navigation .level-top")
    private ExtendedWebElement navigationMenu;

    @FindBy(css = ".block-promo")
    private ExtendedWebElement blockPromoMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isBlockPromoMenuVisible() {
        return blockPromoMenu.isPresent();
    }

    public void search(String query) {
        searchInput.type(query);
        searchInput.submit();
    }

    public LoginPage clickLogin() {
        loginLink.click();
        return new LoginPage(getDriver());
    }

    public void navigateToCategory(String categoryName) {
        // Wait for menu to be visible and clickable
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        // First find and hover over the main category to expose sub-categories
        ExtendedWebElement mainCategory = findExtendedWebElement(By.cssSelector(".nav-sections"));
        mainCategory.hover();

        // Then find and click the specific category using a more robust XPath
        ExtendedWebElement categoryLink = findExtendedWebElement(By.xpath(
                String.format("//nav[@class='navigation']//a[contains(., '%s')]", categoryName)
        ));

        // Add explicit wait before clicking
        categoryLink.click();
    }

    // Added method to select first product
    public ProductPage selectFirstProduct() {
        ExtendedWebElement firstProduct = findExtendedWebElement(By.cssSelector(".product-item"));
        firstProduct.click();
        return new ProductPage(getDriver());
    }

    // Added method to select second product
    public ProductPage selectSecondProduct() {
        List<ExtendedWebElement> products = findExtendedWebElements(By.cssSelector(".product-item"));
        if (products.size() > 1) {
            products.get(1).click();
            return new ProductPage(getDriver());
        }
        throw new RuntimeException("Not enough products to select second product");
    }

    // Added login method
    public void login(String username, String password) {
        LoginPage loginPage = clickLogin();
        loginPage.login(username, password);
    }

    // Added method to navigate to wishlist
    public WishlistPage navigateToWishlist() {
        ExtendedWebElement wishlistLink = findExtendedWebElement(By.cssSelector(".wishlist"));
        wishlistLink.click();
        return new WishlistPage(getDriver());
    }

    // Added method to navigate to compare products
    public CompareProductsPage navigateToCompareProducts() {
        ExtendedWebElement compareLink = findExtendedWebElement(By.cssSelector(".compare-link"));
        compareLink.click();
        return new CompareProductsPage(getDriver());
    }
}