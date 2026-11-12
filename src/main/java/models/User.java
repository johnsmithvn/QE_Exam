package models;

/**
 * [REFACTOR] MỚI - tách từ inner class AdminPageObject.User (code cũ vẫn giữ, đã comment trong AdminPageObject).
 *
 * Tại sao: dữ liệu test không phải là một phần của trang. Khi User là inner class (non-static) của
 * page object thì muốn tạo User phải có sẵn AdminPageObject, và step phải đọc ngược state từ page.
 */
public class User {
    private final String userName;
    private final String password;
    private String employeeName;
    private final String userRole;
    private final String status;

    public User(String userName, String password, String employeeName, String userRole, String status) {
        this.userName = userName;
        this.password = password;
        this.employeeName = employeeName;
        this.userRole = userRole;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    /** Tên nhân viên được chọn ngẫu nhiên từ gợi ý trên UI nên chỉ biết sau khi điền form. */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getStatus() {
        return status;
    }
}
