# [REFACTOR] CŨ: chỉ có tag @exam và tên Feature trùng với API.feature.
# @exam
# Feature: Exam Scenarios
# [REFACTOR] MỚI: thêm tag @ui để Hooks chỉ mở/đóng trình duyệt cho scenario UI; đặt tên Feature riêng.
@exam @ui
Feature: Exam 01 - OrangeHRM user management (UI)



@exam_01
  Scenario: Add new user successfully and search for the user
    Given Open website url
    When I login with valid credentials
    And I click on the "Admin" item on sidebar menu
    # [REFACTOR] CŨ: phải viết kèm khoảng trắng của HTML thì XPath mới khớp.
    # And I click on the " Add " button
    # [REFACTOR] MỚI: XPath dùng normalize-space() nên viết đúng chữ người dùng thấy trên nút.
    And I click on the "Add" button
    And I fill in the add user form with valid data
    And I click on the Save button
    Then Verify new user is created successfully
    And I search for the new user by username
    And Verify new user is displayed in search results
    # [REFACTOR] MỚI: dọn dữ liệu test, không để lại user rác trên site demo dùng chung.
    And I delete the new user



# [REFACTOR] CŨ: scenario rỗng (không có step) lại gắn @exam_02, nên chạy "@exam_02" sẽ kéo theo cả nó
# và report hiện thêm một scenario không kiểm tra gì.
# @exam_02
#   Scenario: Login success for herokuo app
# [REFACTOR] MỚI: tạm comment lại; khi nào viết step cho scenario này thì mở ra và gắn tag riêng.
