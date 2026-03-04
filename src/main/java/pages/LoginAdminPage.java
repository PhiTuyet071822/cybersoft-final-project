package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;


public class LoginAdminPage {
    //    Define attribute
//    Selenium => webDriver
    private WebDriver driver;

    //    Define cac element trong Login page
//    form email
    private By emailField = By.name("email");
    //    form password
    private By passwordField = By.name("password");
    //    button login
    private By loginButton = By.xpath("//button[@type='submit' or text()='Login']");
    //    error message co tren trang web
    private By errorMessage =
            By.cssSelector(".Toastify__toast-body");
    //    endpoint cua page login
    private String loginUrl = "https://demo4.cybersoft.edu.vn/login";

//    ham khoi tao
    public LoginAdminPage(WebDriver driver) {
        this.driver = driver;
    }

//  Ham mo trang login
public void openLoginAdminPage(){
        driver.get(loginUrl);
    }

//    Ham nhap username vao form input
    public void enterEmail(String email){
//        B1: di tim element input username tren web
        WebElement emailElement = driver.findElement(emailField);
//        B2: xoa du lieu cu tren form input neu co
        emailElement.clear();
//        B3: Nhap username vao form input
        emailElement.sendKeys(email);
    }

//    Ham nhap password vao form input
    public void enterPassword(String password){
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }
//    Ham click vao button  login
    public void clickLoginButton(){
        WebElement loginButtonElement = driver.findElement(loginButton);
        loginButtonElement.click();
    }
//    Viet ham Login de tong hop cac buoc login
    public void login(String email, String password){
        openLoginAdminPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
//    Define ham kiem tra co loi hay khong
    public boolean isErrorMessageDisplayed(){
        try{
            WebElement errorElement = driver.findElement(errorMessage);
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
//            vao catch khi khong tim thay element loi
        }
    }
//    Ham get endpointUrl
    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }



}
