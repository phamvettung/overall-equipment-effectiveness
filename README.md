# OVERALL EQUIPMENT EFFECTIVENESS - OEE

### OVERVIEW
The Overall Equipment Effectiveness is a system that collects data of runtime, downtime, cause of machine stopping time. Compute OEE contain: Availability, Performance, Quality. This helps manager make appropriate assessments and adjustmets to improve performance.

### FEATURES
- Collect data of runtime, downtime, cause of machine stopping time via OEE controller installed on CNC machines or connect direct to them.
- Compute A, P, Q, OEE,.. Give statistic charts: OEE, Wasted time, Runtime/Downtime of machine.
- Detailed of summary of machine running, stopping time. Extract data by month and by day
- Auto data collection or Manual data entry.
- Provide APIs for other systems, API security.
- Login, user authentication.
- Manage machine: Add, Update, Delete.
- Access system via Web browser

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
