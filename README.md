# Swaglabs Selenium Test Automation Framework

## Overview

Welcome! This project is a robust and scalable Selenium-based automation framework built for testing the [Swag Labs](https://www.saucedemo.com/) web application. It's crafted using Java, TestNG, Maven, and integrates with ExtentReports for visually rich reporting. The framework is designed with modularity and maintainability in mind, leveraging the Page Object Model (POM) and supporting cross-browser and parallel execution.

Whether you're building regression suites or validating new features, this framework provides everything you need to get started quickly and scale effectively.

---

## Features

- **Page Object Model (POM):** Clean separation of page locators and test logic.
- **Cross-browser support:** Chrome, Firefox, and Edge.
- **Parallel execution:** Configurable via TestNG XML and Maven Surefire plugin.
- **External configurations:** Manage URLs, credentials, and settings via `.properties` files.
- **Automated screenshots:** Captured on test pass/fail/skip for easy debugging.
- **Rich reporting:** ExtentReports integration with custom branding.
- **Log4j2 logging:** Console and file-based logging support.
- **Custom utility classes:** For element actions, JavaScript interactions, and config reading.

---

## Project Structure

```
src/
  main/
    java/
      com.qa.Swaglabs/
        factory/         # WebDriver management
        pages/           # Page classes: LoginPage, HomePage, ProductDetail
        utils/           # Utility classes: ElementUtil, JavaScriptUtil, ReadProperty
  test/
    java/
      com.qa.Swaglabs/
        base/            # BaseTest for setup/teardown
        tests/           # All test classes
        testUtils/       # Screenshot and ExtentReport Listener
    resources/
      config/            # qaconfig.properties for environment settings
      testRunner/        # TestNG XML suite configs
      logo/              # Custom logo for reports
driver/                  # WebDriver executables
reports/                 # Generated HTML test reports
screenshots/             # Captured test screenshots
```

---

## Getting Started

### Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- Compatible WebDriver binaries:
  - ChromeDriver
  - GeckoDriver (Firefox)
  - EdgeDriver  
  *(All included in the `driver/` folder. Make sure driver versions match your installed browsers.)*

---

### Setup & Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/swaglabs-selenium-framework.git
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Configure Browser & URL**
   Edit this file:
   ```
   src/test/resources/config/qaconfig.properties
   ```

4. **Run Tests**
   - All tests:
     ```bash
     mvn test
     ```
   - Specific suite:
     ```bash
     mvn test -DsuiteXmlFile=src/test/resources/testRunner/testng-parallel.xml
     ```
   - Override browser at runtime:
     ```bash
     mvn test -Dbrowser=chrome
     ```

5. **View HTML Reports**
   Navigate to:
   ```
   reports/index.html
   ```

---

## Configuration: `qaconfig.properties`

```properties
browser = edge
testurl = https://www.saucedemo.com/
homePageUrl = https://www.saucedemo.com/inventory.html
username = standard_user
password = secret_sauce
```

You can switch environments or user credentials easily by updating these values. Avoid committing sensitive data when working with shared repositories or CI/CD pipelines.

---

## Parallel Execution

- Controlled through `testng-parallel.xml` and `pom.xml` Surefire plugin.
- Default setup: 2 threads, parallel at the class level.
- To customize, update `thread-count` in the XML or Surefire config block.

---

## Test Scenarios

### LoginPageTest
- Validate login page URL
- Invalid login checks
- Valid login scenario

### HomePageTest
- Validate title and URL
- Add to cart, remove from cart
- Sort product listing

### ProductDetailsTest
- Open product detail page
- Validate product name and pricing

---

## Utility Classes

| Class             | Description                                 |
|------------------|---------------------------------------------|
| `ElementUtil`     | Custom wrapper for Selenium actions         |
| `JavaScriptUtil`  | Scroll, click, highlight using JS           |
| `ReadProperty`    | Reads `.properties` config files            |
| `ScreenshotUtil`  | Captures screenshots on test events         |

---

## Reporting

- **ExtentReports** with dark theme
- HTML report generated after every test run
- Report includes:
  - Pass/Fail/Skip status
  - Screenshots with timestamps
  - Custom logo: `logo/logoNew.png`
- Location: `reports/index.html`

---

## Screenshots

- Captured for **every** test outcome (pass, fail, skip)
- Named with test method and timestamp
- Stored in the `screenshots/` directory
- Automatically cleaned before a new test run

---

## Logging

- Powered by **Log4j2**
- Configurable via `log4j2.properties` in the `resources/logger/` directory
- Logs output to console and optionally to file

---

## Customization

- Replace the default report logo at:  
  `src/test/resources/logo/logoNew.png`
- Adjust ExtentReport configuration in `ExtentListener.java`
- Set custom log levels and file outputs in Log4j2 config

---

## Extending the Framework

- Add new **Page classes** under:  
  `src/main/java/com/qa/Swaglabs/pages/`
- Add new **Test classes** under:  
  `src/test/java/com/qa/Swaglabs/tests/`
- Add shared utility functions under:  
  `src/main/java/com/qa/Swaglabs/utils/`

---

## Troubleshooting

| Issue                        | Solution                                                           |
|-----------------------------|--------------------------------------------------------------------|
| WebDriver not found         | Ensure driver is in `driver/` and compatible with your browser     |
| Browser version mismatch     | Download the correct version of WebDriver                         |
| Test doesn't start           | Check `qaconfig.properties` and suite XML                         |
| Port already in use          | Close previous browser/Selenium sessions                          |
| Report not generated         | Ensure `ExtentListener` is correctly added to your test class      |

---

## Contributing

Found a bug or want to add a feature?  
You're welcome to fork the repo, create a branch, make changes, and open a pull request.

---

## Author

**Abhishek S Rathore**

---

## Contact

Have a question, suggestion, or idea?  
Feel free to raise an issue or connect with me on [abhishek.rathore1177@thepsi.com].

---