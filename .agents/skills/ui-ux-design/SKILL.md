---
name: ui-ux-design
description: >-
  Nguyên tắc thiết kế UI/UX hiện đại, tối giản (Minimalist, Clean Design), loại bỏ các thiết kế rườm rà dạng AI-slop (gradient quá đà, bóng đổ gắt, badge quá nhiều). Áp dụng cho các trang web và ứng dụng frontend.
---

# Hướng Dẫn Thiết Kế UI/UX Tối Giản & Tinh Tế (Minimalist UI/UX Guidelines)

Kỹ năng này định hình phong cách thiết kế giao diện người dùng theo hướng tối giản, hiện đại, thanh lịch (lấy cảm hứng từ Apple, Notion, Linear, Stripe), loại bỏ hoàn toàn cảm giác "AI tạo mẫu rập khuôn, rối mắt".

---

## 1. Loại Bỏ "AI Slop" & Yếu Tố Gây Rối Mắt
- **Không dùng gradient sặc sỡ**: Tránh phối màu gradient tím-hồng-xanh gắt trên các khối to (như Hero banner).
- **Không lạm dụng Badge**: Hạn chế gắn quá nhiều nhãn màu mè lên ảnh hay tiêu đề.
- **Bóng đổ tinh tế (Subtle Shadows)**: Tránh bóng đen đậm (`rgba(0,0,0,0.2)`). Sử dụng viền mỏng (`border: 1px solid #e5e7eb`) kết hợp bóng rất nhẹ (`box-shadow: 0 1px 3px rgba(0,0,0,0.05)`).
- **Tránh các khối chiếm diện tích vô nghĩa**: Bỏ các panel debug to đùng, slogan dài dòng. Tập trung ngay vào nội dung và chức năng chính.

---

## 2. Hệ Thống Màu Sắc & Typography
- **Màu nền**: Trắng (`#ffffff`) hoặc xám nhạt (`#f9fafb`, `#f8fafc`).
- **Màu văn bản**:
  - Tiêu đề & nội dung chính: `#111827` (Gray-900) hoặc `#0f172a` (Slate-900).
  - Phụ đề, mô tả phụ: `#6b7280` (Gray-500) hoặc `#64748b`.
  - Đường viền: `#e5e7eb` (Gray-200) hoặc `#f1f5f9`.
- **Màu điểm nhấn (Accent)**: Chỉ chọn 1 màu chủ đạo tinh tế (ví dụ: `#0f172a` đen tối giản, hoặc `#2563eb` xanh công nghệ).
- **Typography**:
  - Font: `Inter`, `Plus Jakarta Sans` hoặc `system-ui`.
  - Phân cấp rõ ràng: Tiêu đề 18-24px semi-bold, Body 14-15px regular, Meta 12-13px.

---

## 3. Bố Cục & Tương Tác
- **Thanh điều hướng (Navbar)**: Mỏng nhẹ, nền trắng viền mỏng dưới, logo và menu tinh tế.
- **Bộ lọc & Sắp xếp**: Dạng Tabs phẳng hoặc Pills nền xám nhẹ, khi chọn thì đổi sang nền đen/xanh đậm hoặc chữ in đậm.
- **Card sản phẩm**:
  - Khung ảnh tỉ lệ chuẩn (1:1 hoặc 4:3), nền xám sáng, bo góc nhẹ (8px - 10px).
  - Tên sản phẩm rõ ràng, giá tiền nổi bật, nút hành động gọn gàng.
- **Bảng dữ liệu (Admin Table)**:
  - Header nền xám nhạt, chữ hoa nhỏ gọn.
  - Hàng bảng có padding vừa phải (12px), hover sáng nhẹ, viền ngang mảnh.
