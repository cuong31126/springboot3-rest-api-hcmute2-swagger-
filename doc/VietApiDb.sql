-- ====================================================================
-- SCRIPT TẠO DATABASE VÀ DỮ LIỆU MẪU CHO DỰ ÁN VIETAPI SPRING BOOT 3
-- Hệ quản trị CSDL: Microsoft SQL Server
-- Tác giả: ThS. Nguyễn Hữu Trung (HCMUTE - FIT)
-- ====================================================================

-- 1. TẠO CƠ SỞ DỮ LIỆU NẾU CHƯA TỒN TẠI
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'VietApiDb')
BEGIN
    CREATE DATABASE VietApiDb;
    PRINT N'Đã tạo cơ sở dữ liệu VietApiDb thành công!';
END
GO

USE VietApiDb;
GO

-- ====================================================================
-- 2. TẠO BẢNG CATEGORIES (Danh mục sản phẩm)
-- ====================================================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Categories')
BEGIN
    CREATE TABLE Categories (
        categoryId BIGINT IDENTITY(1,1) PRIMARY KEY,
        categoryName NVARCHAR(200) NOT NULL,
        icon NVARCHAR(500) NULL
    );
    PRINT N'Đã tạo bảng Categories thành công!';
END
GO

-- ====================================================================
-- 3. TẠO BẢNG PRODUCTS (Sản phẩm)
-- ====================================================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Products')
BEGIN
    CREATE TABLE Products (
        productId BIGINT IDENTITY(1,1) PRIMARY KEY,
        productName NVARCHAR(500) NOT NULL,
        quantity INT NOT NULL DEFAULT 0,
        unitPrice FLOAT NOT NULL DEFAULT 0,
        images NVARCHAR(200) NULL,
        description NVARCHAR(500) NOT NULL,
        discount FLOAT NOT NULL DEFAULT 0,
        createDate DATETIME2 NOT NULL DEFAULT GETDATE(),
        status SMALLINT NOT NULL DEFAULT 1,
        categoryId BIGINT NULL,
        CONSTRAINT FK_Products_Categories FOREIGN KEY (categoryId) 
            REFERENCES Categories(categoryId) ON DELETE SET NULL ON UPDATE CASCADE
    );
    PRINT N'Đã tạo bảng Products thành công!';
END
GO

-- ====================================================================
-- 4. CHÈN DỮ LIỆU MẪU (Sample Data theo đúng Slide bài giảng)
-- ====================================================================

-- Xóa dữ liệu cũ nếu muốn làm mới (tùy chọn)
-- DELETE FROM Products;
-- DELETE FROM Categories;
-- DBCC CHECKIDENT ('Products', RESEED, 0);
-- DBCC CHECKIDENT ('Categories', RESEED, 0);

-- Chèn dữ liệu mẫu cho Categories
IF NOT EXISTS (SELECT * FROM Categories)
BEGIN
    SET IDENTITY_INSERT Categories ON;

    INSERT INTO Categories (categoryId, categoryName, icon) VALUES 
    (1, N'Quần Áo Nam', N'category/687f3967b7c2fe6a134a2c11894eea4b_tn.png'),
    (2, N'Quần Áo Nữ', N'category/687f3967b7c2fe6a134a2c11894eea4b_tn.png'),
    (3, N'Quần Áo Trẻ Em', N'category/687f3967b7c2fe6a134a2c11894eea4b_tn.png'),
    (4, N'Giày Thể Thao', N'category/687f3967b7c2fe6a134a2c11894eea4b_tn.png'),
    (5, N'Túi Xách & Balo', N'category/687f3967b7c2fe6a134a2c11894eea4b_tn.png');

    SET IDENTITY_INSERT Categories OFF;
    PRINT N'Đã chèn 5 danh mục mẫu vào Categories!';
END
GO

-- Chèn dữ liệu mẫu cho Products
IF NOT EXISTS (SELECT * FROM Products)
BEGIN
    SET IDENTITY_INSERT Products ON;

    INSERT INTO Products (productId, productName, quantity, unitPrice, images, description, discount, createDate, status, categoryId) VALUES 
    (1, N'Áo Thun Nam Cotton Cổ Tròn', 100, 150000, N'p1.png', N'Chất liệu cotton 100% thấm hút mồ hôi cực tốt, thoáng mát', 10, GETDATE(), 1, 1),
    (2, N'Áo Sơ Mi Nam Công Sở Dài Tay', 50, 280000, N'p2.png', N'Chống nhăn cao cấp, form slim-fit trẻ trung', 5, GETDATE(), 1, 1),
    (3, N'Đầm Xòe Nữ Dự Tiệc Dáng Dài', 40, 390000, N'p3.png', N'Thiết kế sang trọng, vải voan mềm mại 2 lớp', 15, GETDATE(), 1, 2),
    (4, N'Chân Váy Chữ A Nữ Xếp Ly', 60, 195000, N'p4.png', N'Chân váy công sở dễ phối đồ, tôn dáng', 0, GETDATE(), 1, 2),
    (5, N'Bộ Đồ Trẻ Em Siêu Nhân Vải Cotton', 80, 120000, N'p5.png', N'Bộ thun cotton an toàn cho làn da nhạy cảm của bé', 0, GETDATE(), 1, 3),
    (6, N'Giày Sneaker Nam Cổ Thấp', 35, 450000, N'p6.png', N'Đế cao su êm ái, bám đường tốt, phù hợp chạy bộ', 20, GETDATE(), 1, 4);

    SET IDENTITY_INSERT Products OFF;
    PRINT N'Đã chèn 6 sản phẩm mẫu vào Products!';
END
GO

-- ====================================================================
-- 5. KIỂM TRA LẠI KẾT QUẢ
-- ====================================================================
SELECT * FROM Categories;
SELECT * FROM Products;
GO
