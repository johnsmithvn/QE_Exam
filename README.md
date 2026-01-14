# QE Exam - Selenium & API Automation Testing

## Setup Instructions

### Prerequisites
- JDK 8 or higher
- Maven 3.6+
- Chrome browser
- GitHub Personal Access Token (for API tests)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd QE_Exam
   ```

2. **Configure GitHub Token (for API tests)**
   - Create a Personal Access Token at: https://github.com/settings/tokens
   - Select scope: `public_repo` (read access to public repositories)
   - Open `github.properties` file in the project root
   - Replace `YOUR_GITHUB_TOKEN_HERE` with your actual token:
     ```properties
     github.token=ghp_xxxxxxxxxxxxxxxxxxxxx
     ```

3. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

## How to Run test

### Option 1: Run via Command Line

Run the UI test scenario for adding and searching a new user:

```bash
mvn clean test -Dcucumber.options="--tags @exam_01"
```
OR
``` bash
mvn clean test -Dcucumber.options="--tags @exam_02"

```

### Option 2: Run via Eclipse IDE

1. **Import project into Eclipse:**
   - Open Eclipse
   - File → Import → Maven → Existing Maven Projects
   - Browse to `QE_Exam` folder and select it
   - Click Finish

2. **Run tests:**
   - Navigate to `src/test/java/cucumberOption/TestRunner.java`
   - Right-click on the file
   - Select **Run As → JUnit Test**
   - Tests will execute and results will appear in JUnit view

3. **Run specific scenario:**
   - Open `TestRunner.java`
   - Change `tags` parameter:
     - For UI test: `tags = "@exam_01"`
     - For API test: `tags = "@exam_02"`


## Test Execution Results

### Console Output

After running tests, you will see:

**For API tests (@exam_02):**
```
========== GITHUB ANALYSIS RESULT ==========
Organization: SeleniumHQ
Total repositories: 21
Total open issues across all repositories: XXXX
Highest-rated repository: selenium
Stars: XXXXX
============================================
```

**For UI tests (@exam_01):**
- User creation success message
- Search results verification
- All assertions passed

### Test Report

View the detailed Cucumber HTML report:

1. Open `target/cucumber-html-report/index.html` in a browser
2. Or view JSON report at `target/site/test.json`

**Test Report includes:**
- Scenario pass/fail status
- Step-by-step execution details
- Execution time
- Screenshots (if configured)

## Project Structure

```
QE_Exam/
├── src/
│   ├── main/java/
│   │   ├── API/
│   │   │   ├── GitHubApi.java          # GitHub REST API client
│   │   │   └── RepoResult.java         # Result model
│   │   ├── commons/
│   │   │   ├── CommonFuntions.java     # Reusable utilities
│   │   │   └── GlobalConstants.java    # Configuration constants
│   │   └── pages/
│   │       ├── pageObjects/            # Page Object classes
│   │       └── pageUIs/                # Page locators
│   └── test/
│       ├── java/
│       │   ├── cucumberOption/
│       │   │   ├── Hooks.java          # Browser setup/teardown
│       │   │   └── TestRunner.java     # Cucumber test runner
│       │   └── stepDefinitions/        # Gherkin step implementations
│       └── resources/
│           └── Login.feature           # BDD test scenarios
├── github.properties                    # GitHub token configuration
├── pom.xml                             # Maven dependencies
└── README.md                           # This file
```

## Technologies Used

- **Java** - Programming language
- **Selenium WebDriver** - UI automation
- **RestAssured** - API testing
- **Cucumber** - BDD framework
- **JUnit** - Assertions
- **Maven** - Build tool
- **WebDriverManager** - Automatic driver management


