# PHẦN MỀM QUẢN LÝ HIỆU SUẤT MÁY TỔNG THỂ - OEE
### TỔNG QUAN
Hệ thống thu thập dữ liệu thời gian máy chạy, máy dừng, các nguyên nhân gây dừng máy khác nhau. Tính toán OEE gồm: Sự sẵn sàng (Availability), Hiệu suất (Performance), Chất lượng (Quality). Từ đó giúp người quản lý có những đánh giá và điều chỉnh phù hợp để nâng cao hiệu suất.
### CÁC CHỨC NĂNG
- Thu thập dữ liệu thời gian máy dừng, thời gian máy, nguyên nhân dừng máy qua các Bộ điều khiển OEE lắp trên máy CNC và kết nối trực tiếp tới máy CNC.
- Hiển thị các chỉ số A, P, Q, OEE,.. Đưa ra biểu đồ thống kê: Hiệu suất OEE, Thời gian lãng phí, Thời gian chạy máy/dừng máy.
- Tổng hợp chi tiết thời gian máy chạy, máy dừng. Trích xuất dữ liệu theo từng tháng, từng ngày.
- Thu thập dữ liệu tự động hoặc nhập liệu bằng tay.
- Cung cấp các API cho các hệ thống khác lấy dữ liệu.
- Tính năng đăng nhập, xác thực người dùng.
- Quản lý máy: thêm, sửa, xóa.
- Hệ thống hoạt động trong mạng LAN cục bộ, người dùng có thể truy cập giám sát từ xa thông qua trình duyệt web.

### SƠ ĐỒ KẾT NỐI
![overall equipment effectiveness](/assets/oee_diagram.PNG)

### CÁC CÔNG NGHỆ SỬ DỤNG
- Java Spring boot, MS SQL Server
- Ngôn ngữ: Java, CSS, Javascript
