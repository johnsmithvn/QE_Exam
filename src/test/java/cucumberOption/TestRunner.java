package cucumberOption;

import io.cucumber.junit.Cucumber;


import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
// [REFACTOR] CŨ:
// @CucumberOptions(features = "src/test/resources/", glue = "stepDefinitions", monochrome = true, dryRun = false, plugin = {
//                 "pretty", "html:target/report", // day la folder
//                 "json:target/site/sua-ten.json" },
//                 tags = "@exam_01")
// [REFACTOR] MỚI - thay đổi gì và tại sao:
// - glue thêm "cucumberOption": để Cucumber nạp Hooks (@Before/@After). Bản cũ chỉ quét "stepDefinitions"
//   nên hook trong Hooks không bao giờ được Cucumber gọi.
// - bỏ tags = "@exam_01" gắn cứng: chọn scenario lúc chạy bằng -Dcucumber.filter.tags="@exam_01"
//   (không truyền thì chạy tất cả), không phải sửa code mỗi lần đổi bài.
// - đổi tên report "sua-ten.json" (tên tạm) thành "cucumber.json".
@CucumberOptions(features = "src/test/resources/", glue = { "stepDefinitions", "cucumberOption" }, monochrome = true, dryRun = false, plugin = {
                "pretty", "html:target/report", // day la folder
                "json:target/site/cucumber.json" })

public class TestRunner {

}
