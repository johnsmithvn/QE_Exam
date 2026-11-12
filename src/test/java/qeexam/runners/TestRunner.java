package qeexam.runners;

// [REFACTOR] ĐỔI PACKAGE: cucumberOption -> qeexam.runners (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

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
// - glue thêm package chứa Hooks: để Cucumber nạp Hooks (@Before/@After). Bản cũ chỉ quét "stepDefinitions"
//   nên hook trong Hooks không bao giờ được Cucumber gọi.
// - bỏ tags = "@exam_01" gắn cứng: chọn scenario lúc chạy bằng -Dcucumber.filter.tags="@exam_01"
//   (không truyền thì chạy tất cả), không phải sửa code mỗi lần đổi bài.
// - đổi tên report "sua-ten.json" (tên tạm) thành "cucumber.json".
// - features trỏ vào thư mục con "features" (file .feature đã tách khỏi file config, chia theo ui/ và api/);
//   glue đổi theo tên package mới: qeexam.stepdefinitions (step) và qeexam.hooks (Hooks, TestContext).
@CucumberOptions(features = "src/test/resources/features", glue = { "qeexam.stepdefinitions", "qeexam.hooks" }, monochrome = true, dryRun = false, plugin = {
                "pretty", "html:target/report", // day la folder
                "json:target/site/cucumber.json" })

public class TestRunner {

}
