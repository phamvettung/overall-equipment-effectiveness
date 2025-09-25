# OVERALL EQUIPMENT EFFECTIVENESS - OEE

### OVERVIEW
The Overall Equipment Effectiveness is a system that collects data of runtime, downtime, cause of machine stopping time. Compute OEE contain: Availability, Performance, Quality. This helps manager make appropriate assessments and adjustmets to improve performance.

### CÁC CHỨC NĂNG
- Thu thập dữ liệu thời gian máy dừng, thời gian máy, nguyên nhân dừng máy qua các Bộ điều khiển OEE lắp trên máy CNC và kết nối trực tiếp tới máy CNC.
- Tính các chỉ số A, P, Q, OEE,.. Đưa ra biểu đồ thống kê: Hiệu suất OEE, Thời gian lãng phí, Thời gian chạy máy/dừng máy.
- Tổng hợp chi tiết thời gian máy chạy, máy dừng. Trích xuất dữ liệu theo từng tháng, từng ngày.
- Thu thập dữ liệu tự động hoặc nhập liệu bằng tay.
- Cung cấp các API cho các hệ thống khác, bảo mật API.
- Tính năng đăng nhập, xác thực người dùng.
- Quản lý máy: thêm, sửa, xóa.
- Hệ thống hoạt động trong mạng LAN cục bộ, người dùng có thể truy cập giám sát từ xa thông qua trình duyệt web.

### SƠ ĐỒ KẾT NỐI
![overall equipment effectiveness](/assets/oee_diagram.PNG)

- Phần mềm OEE Client kết nối tới OEE Server thông qua RESTful API.
- OEE Client kết nối tới các Bộ điều khiển OEE, thu thập các nguyên nhân dừng máy thông qua việc nhấn nút từ người vân hành.
- OEE Client kết nối tới các Máy CNC để thu thập thời gian máy hoạt động, số dụng cụ được sử dụng, số lần thay dụng cụ,... Giao thức kết nối được mô tả ở đây:
https://www.haascnc.com/service/troubleshooting-and-how-to/how-to/machine-data-collection---ngc.html

### CẤU TRÚC BẢNG
![overall equipment effectiveness](/assets/structure_db.PNG)

- Bảng machine: quản lý các máy CNC. Thêm mới, chỉnh sửa, xóa.
- Bảng input: thu thập dữ liệu dừng máy theo các nguyên nhân khác nhau.
- Bảng machine_data_collection: thu thập thời gian máy hoạt động, trạng thái hiện tại của máy (running/stopped),...
- Bảng account: quản lý, xác thực người dùng.

### CÁC CÔNG NGHỆ SỬ DỤNG
- Java Spring Boot, Spring Security JWT, Hibernate, JPA.
- Hệ Quản trị CSDL: MS SQL Server.

### DEMO
[Watch demo video](./assets/oee_webapp.mp4)
###
https://github.com/user-attachments/assets/688a3032-78ff-4c33-b872-35e01cfb0d6b
### API TESTING
https://github.com/user-attachments/assets/fe55e83c-89db-47f0-858b-9f42a2b25e60
