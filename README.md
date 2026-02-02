# CyberSoft Final Automation Testing Project

This repository contains an end-to-end **Selenium + TestNG Automation Framework** built as a final project for the CyberSoft Automation Testing course.

The framework is designed following **Page Object Model (POM)** principles, with a strong focus on:
- Clean architecture
- Stable locators
- Reusable utilities
- Readable and maintainable test cases

---

## 🚀 Tech Stack

- **Language:** Java (JDK 21)
- **Automation Tool:** Selenium WebDriver 4
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)
- **Browser:** Google Chrome
- **IDE:** IntelliJ IDEA

---

## 📁 Project Structure

```text
cybersoft-final-automation
├── src
│   ├── main
│   │   └── java
│   │       └── com.NguyenHoangPhiTuyet
│   │           ├── pages          # Page Object classes
│   │           └── utils          # WaitUtils, DriverFactory, helpers
│   └── test
│       └── java
│           └── com.NguyenHoangPhiTuyet
│               ├── tests          # Test cases (TestNG)
│               └── data           # Test data classes
├── testng.xml                     # Test Suite configuration
├── pom.xml                        # Maven dependencies
└── README.md

🧩 Framework Highlights
Page Object Model for clean separation between test logic and UI logic

Centralized WaitUtils (explicit waits only, no implicit wait)

Reusable clearAndType() to prevent duplicated input data

Stable locators (CSS / XPath optimized)

Test cases are independent, readable, and easy to maintain

Easy to extend (add new pages or new test cases with minimal effort)

✅ Implemented Test Modules
🔐 Authentication
Register

TC01: Register successfully

TC03: Register with existing email

Login

TC05: Login success

TC06: Login with invalid credentials

TC07: Logout and session clear

TC08: Validate error messages (required fields / toast)

👤 User Profile & Dashboard
TC53: View public profile information

TC54: Edit profile information successfully

▶️ How to Run Tests
Option 1: Run via IntelliJ IDEA
1. Open project in IntelliJ IDEA

2. Open testng.xml

3. Right click → Run

Option 2: Run via Maven

mvn clean test

⚙️ Key Design Decisions
- No implicit wait is used to avoid unpredictable behavior

- All waits are handled via WaitUtils

- Profile update actions always clear existing data before typing

- Assertions are placed only at test level (not inside Page Objects)

- Locators are scoped to specific sections to improve stability

📌 Notes
- Chrome version may show CDP warnings — these do not affect test execution

- This project is intended for learning, portfolio, and demonstration purposes

- Framework structure follows industry-standard automation practices

👤 Author
Nguyễn Hoàng Phi Tuyết
Automation Testing Student – CyberSoft

