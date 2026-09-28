# BÀI TẬP: RESTFUL API, SWAGGER 3 & GRAPHQL TRONG SPRING BOOT 3

> **Môn học**: Lập trình Web (WEBPR330479) - Trường ĐH Sư Phạm Kỹ Thuật TP.HCM (HCMUTE)  
> **Giảng viên**: ThS. Nguyễn Hữu Trung  
> **Công nghệ**: Spring Boot 3.3.4, Java 21, Spring Data JPA, Microsoft SQL Server, SpringDoc OpenAPI 3, Spring GraphQL, Thymeleaf, jQuery 3.7.1, Bootstrap 5.

---

## 📌 Tóm Tắt Các Mục Đã Hoàn Thành Trong Dự Án

### 1. Kiến Trúc RESTful API Chuẩn (Theo bài giảng UTE LMS 1452697)
* Định dạng chuẩn phản hồi `Response.java` gồm 3 trường: `{ status: Boolean, message: String, body: Object }`.
* Sử dụng đầy đủ các phương thức HTTP: `GET`, `POST`, `PUT`, `DELETE` với các mã phản hồi chuẩn: `200 OK`, `400 BAD_REQUEST`, `404 NOT_FOUND`.

### 2. CRUD API Category & Product Kèm File Upload (Theo hướng dẫn bài giảng)
* **Entity**: `Category` liên kết 1-N với `Product` qua `jakarta.persistence.*` và `@JsonIgnore`.
* **Upload File**: Tầng Service `FileSystemStorageServiceImpl` lưu trữ file ảnh vật lý vào thư mục `uploads/` có chống path-traversal và sinh mã UUID ngẫu nhiên.
* **REST API Controller**:
  * `CategoryAPIController`: CRUD Category, upload icon mới hoặc giữ nguyên icon cũ khi sửa.
  * `ProductApiController`: CRUD Product, upload ảnh đại diện, liên kết Category.
  * `ImageController`: Phục vụ render và xem trực tiếp ảnh tại `/images/{filename}` và `/api/upload`.

### 3. Cấu Hình Swagger 3 / OpenAPI (Theo hướng dẫn Swagger 3)
* Tích hợp thư viện `springdoc-openapi-starter-webmvc-ui` 2.6.0 (chuẩn OpenAPI 3 cho Spring Boot 3 & Jakarta EE).
* Tạo class cấu hình `OpenApiConfig.java` cho phép test trực quan mọi API tại: `http://localhost:8080/swagger-ui/index.html`.

### 4. Giao Diện Client AJAX CRUD Category & Product (Theo hướng dẫn AJAX REST)
* **Giao diện Category AJAX**: `http://localhost:8080/category-ajax.html`
  * Hiển thị bảng, ảnh icon, Modal Bootstrap Thêm mới, Modal Sửa, nút Xóa kèm hiệu ứng mờ dần `fadeOut`.
* **Giao diện Product AJAX**: `http://localhost:8080/product-ajax.html`
  * Hiển thị bảng sản phẩm, giá tiền, giảm giá, tồn kho, chọn danh mục liên kết, Thêm/Sửa/Xóa sản phẩm bằng AJAX qua `FormData`.

### 5. GraphQL API & Giao Diện AJAX Thymeleaf Tối Giản (Theo bài giảng GraphQL)
* Tích hợp `spring-boot-starter-graphql` và kích hoạt công cụ **GraphiQL IDE**: `http://localhost:8080/graphiql`.
* Schema chuẩn `schema.graphqls` hỗ trợ:
  * **Trang Home**: Hiển thị tất cả sản phẩm sắp xếp giá từ thấp đến cao (`productsSortedByPriceAsc`).
  * **Trang Home**: Lấy tất cả sản phẩm của 01 Category (`productsByCategory(categoryId)`).
  * **Trang Admin**: CRUD đầy đủ, tìm kiếm và phân trang dữ liệu cho cả **Product** (`productsPaged`) và **Category** (`categoriesPaged`).
  * **Tích hợp Upload File**: Hỗ trợ chọn file ảnh từ máy tính, xem trước ảnh và tự động upload qua AJAX khi Thêm & Sửa.
* **Giao diện Client Thymeleaf Tối Giản (Minimalist UI/UX)**:
  * Loại bỏ hoàn toàn các khối rối mắt "AI-slop" (bỏ banner màu mè, bỏ inspector choán chỗ).
  * Bảng màu trung tính tinh tế (Apple / Linear style), font Inter hiện đại, thanh chuyển tab Segmented Control, trải nghiệm mượt mà không reload trang.

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án

### Yêu cầu hệ thống:
* **Java SDK**: Phiên bản 21 (hoặc 17+)
* **Maven**: 3.8+ (hoặc dùng Maven tích hợp sẵn trong IDE)
* **Hệ quản trị CSDL**: Microsoft SQL Server (cổng mặc định 1433)
* **IDE khuyên dùng**: Spring Tool Suite (STS) 4 / Eclipse / IntelliJ IDEA / VS Code

---

### Bước 1: Clone mã nguồn về máy
```bash
git clone https://github.com/cuong31126/springboot3-rest-api-hcmute2-swagger-.git
cd vietapibt2
```

---

### Bước 2: Tạo Cơ sở dữ liệu & Dữ liệu mẫu (SQL Server)
1. Mở **SQL Server Management Studio (SSMS)** hoặc công cụ dòng lệnh `sqlcmd`.
2. Mở file **`VietApiDb.sql`** (có sẵn ở thư mục gốc dự án) và bấm **Execute**.
3. Script này sẽ tự động:
   * Tạo Database `VietApiDb`.
   * Tạo 2 bảng `Categories` và `Products` với khóa ngoại liên kết.
   * Chèn sẵn dữ liệu danh mục và sản phẩm mẫu.

*(Nếu dùng dòng lệnh: `sqlcmd -S localhost -U sa -P your_password -C -i VietApiDb.sql`)*

---

### Bước 3: Cấu hình kết nối CSDL (Nếu cần)
Mở file `src/main/resources/application.properties` và kiểm tra lại tài khoản SQL Server của máy bạn:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=VietApiDb;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=your_password
```
*(Hãy đổi `password` thành mật khẩu SQL Server của bạn nếu khác).*

---

### Bước 4: Khởi chạy ứng dụng

#### Cách 1: Chạy bằng dòng lệnh (Terminal):
```powershell
mvn spring-boot:run
```

#### Cách 2: Chạy trên Spring Tool Suite (STS) / Eclipse:
1. Chọn **File -> Open Projects from File System...** -> chọn thư mục `vietapibt2`.
2. Nhấp chuột phải vào dự án -> chọn **Run As -> Spring Boot App**.

---

## 🌐 Các Đường Dẫn Kiểm Thử Dự Án

| STT | Chức năng | Đường dẫn URL | Mô tả |
| :--- | :--- | :--- | :--- |
| **1** | **Trang chủ GraphQL E-Commerce** | [http://localhost:8080/](http://localhost:8080/) | Lọc Category, Sắp xếp giá Thấp &rarr; Cao bằng GraphQL AJAX |
| **2** | **Trang Quản trị GraphQL** | [http://localhost:8080/admin](http://localhost:8080/admin) | CRUD, Tìm kiếm, Phân trang Category & Product + Upload File |
| **3** | **GraphiQL IDE Explorer** | [http://localhost:8080/graphiql](http://localhost:8080/graphiql) | Giao diện soạn thảo và test trực tiếp các câu query/mutation GraphQL |
| **4** | **Tài liệu Swagger 3 UI** | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | Giao diện kiểm thử toàn bộ RESTful API |
| **5** | **Category REST AJAX Client** | [http://localhost:8080/category-ajax.html](http://localhost:8080/category-ajax.html) | Quản lý Category qua REST API bằng AJAX (Mục 4) |
| **6** | **Product REST AJAX Client** | [http://localhost:8080/product-ajax.html](http://localhost:8080/product-ajax.html) | Quản lý Product qua REST API bằng AJAX (Mục 4) |
| **7** | **REST API Category Endpoint** | [http://localhost:8080/api/category](http://localhost:8080/api/category) | Trả về JSON toàn bộ danh mục theo format Response |
| **8** | **REST API Product Endpoint** | [http://localhost:8080/api/product](http://localhost:8080/api/product) | Trả về JSON toàn bộ sản phẩm theo format Response |

---
*Tác giả bài tập: Sinh viên HCMUTE - Năm học 2026*
