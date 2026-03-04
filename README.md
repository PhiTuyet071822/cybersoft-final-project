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
🔐1. Authentication & User Management (6 TC)
TC01: Đăng ký thành công (Buyer)

TC03: Đăng ký thất bại - Email đã tồn tại

TC05: Đăng nhập thành công

TC06: Đăng nhập thất bại - Invalid credentials

TC07: Đăng xuất thành công và xóa session

TC08: Kiểm tra hiển thị thông báo lỗi (Required fields / Toast)

2. Search & Filter Services (4 TC)
TC10: Tìm kiếm Gig theo từ khóa

TC11: Lọc Gig theo Category

TC13: Lọc Gig theo khoảng giá (Price range)

TC18: Clear all filters (Xóa bộ lọc)

3. Gig Management - Seller (4 TC)
TC20: Tạo Gig mới thành công

TC23: Chỉnh sửa Gig đã tạo

TC25: Xóa Gig khỏi danh sách

TC26: Kiểm tra các trường bắt buộc khi tạo Gig

4. Order & Purchase Flow (4 TC)
TC29: Xem chi tiết Gig

TC30: Chọn Package và Checkout thành công

TC34: Đặt hàng thất bại khi chưa đăng nhập

TC37: Kiểm tra thông tin đơn hàng sau khi đặt thành công

5. Review & Rating (2 TC)
TC47: Buyer đánh giá dịch vụ sau khi hoàn thành đơn hàng

TC50: Hiển thị đánh giá chính xác trên trang chi tiết Gig

6. User Profile & Dashboard (3 TC)
TC53: Xem thông tin Profile công khai

TC54: Cập nhật thông tin cá nhân thành công

TC55: Tải lên/Thay đổi ảnh đại diện (Avatar)

7. Messages & Communication (2 TC)
TC62: Gửi tin nhắn cho người bán (Seller)

TC63: Phản hồi tin nhắn (Reply) trong hệ thống

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

