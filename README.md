# PHẦN MỀM TÍNH HIỆU SUẤT MÁY CNC - OEE
![overall equipment effectiveness](/assets/dashboard.png)
### GIỚI THIỆU
Phần mềm thu thập dữ liệu thời gian máy chạy, thời gian máy dừng từ các Hộp điều khiển OEE. Tính toán OEE gồm: Sự sẵn sàng (Availability), Hiệu suất (Performance), Chất lượng (Quality). Từ đó giúp người quản lý có những đánh giá và điều chỉnh phù hợp để nâng cao hiệu suất. Hệ thống hoạt động trong mạng LAN cục bộ, người dùng có thể truy cập để giám sát từ xa thông qua trình duyệt web.
### CÁC CHỨC NĂNG
- Thu thập dữ liệu thời gian máy dừng, thời gian máy chạy qua các Hộp điều khiển OEE lắp trên máy CNC, thông qua kết nối TCP/IP.
- Hiển thị các chỉ số A, P, Q, OEE,...
- Đưa ra biểu đồ thống kê: Hiệu suất OEE, Thời gian lãng phí, Thời gian chạy máy/dừng máy.
- Tổng hợp chi tiết thời gian máy dừng. Tính OEE theo ngày và theo tháng.
### CÁC CÔNG NGHỆ SỬ DỤNG
- Java Spring boot, MS SQL Server
- Ngôn ngữ: Java, CSS, Javascript

![overall equipment effectiveness](/assets/chart_1.JPG)
![overall equipment effectiveness](/assets/database.JPG)
