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
   - Copy the file `github.properties example` in the project root to `github.properties`
     (this file is git-ignored, so the token is never committed)
   - Replace `YOUR_GITHUB_TOKEN_HERE` with your actual token:
     ```properties
     github.token=ghp_xxxxxxxxxxxxxxxxxxxxx
     ```
   - Alternatively, set the environment variable `GITHUB_TOKEN` (it takes priority over the file)
   - Without a token the API test still runs, but GitHub limits unauthenticated calls to 60 requests/hour

3. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

## How to Run test

### Option 1: Run via Command Line

Run the UI test scenario for adding and searching a new user:

```bash
mvn clean test -Dcucumber.filter.tags="@exam_01"
```

Run the API test scenario for the GitHub organization analysis:

```bash
mvn clean test -Dcucumber.filter.tags="@exam_02"
```

Run everything (no tag filter):

```bash
mvn clean test
```

Other tags: `@ui` (all UI scenarios), `@api` (all API scenarios).

### Configuration

UI test settings live in `src/test/resources/config.properties` and can be overridden on the command line:

| Key | Meaning |
|---|---|
| `ui.url` | Login page URL of the site under test |
| `ui.username` / `ui.password` | Account used to log in |
| `chrome.args` | Extra Chrome arguments, comma separated (command line only) |
| `github.api.url` | GitHub API base URL (command line only, default `https://api.github.com`) |

Example - run the UI test headless:

```bash
mvn clean test -Dcucumber.filter.tags="@exam_01" -Dchrome.args=--headless=new,--window-size=1920,1080
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
   - `TestRunner.java` has no hard-coded tag, so it runs every scenario by default
   - To run one scenario: Run As → Run Configurations… → Arguments → VM arguments, then add
     - For UI test: `-Dcucumber.filter.tags="@exam_01"`
     - For API test: `-Dcucumber.filter.tags="@exam_02"`


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

The API test also asserts that the highest-rated repository is `selenium` and that the number of
repositories fetched matches the organization's `public_repos` count.

**For UI tests (@exam_01):**
- User creation success message
- Search results verification
- The created user is deleted again (test data clean-up)
- All assertions passed

### Test Report

View the detailed Cucumber HTML report:

1. Open `target/report/index.html` in a browser
2. Or view JSON report at `target/site/cucumber.json`
3. For the richer report, run `mvn clean verify` (instead of `test`) and open
   `target/site/cucumber-html/cucumber-html-reports/overview-features.html`

**Test Report includes:**
- Scenario pass/fail status
- Step-by-step execution details
- Execution time
- Screenshot of the browser when a UI scenario fails


## Technologies Used

- **Java** - Programming language
- **Selenium WebDriver** - UI automation
- **RestAssured** - API testing
- **Cucumber** - BDD framework
- **JUnit** - Assertions
- **Maven** - Build tool
- **WebDriverManager** - Automatic driver management


