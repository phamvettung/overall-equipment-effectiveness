# OVERALL EQUIPMENT EFFECTIVENESS - OEE

### OVERVIEW
The Overall Equipment Effectiveness is a system that collects data of runtime, downtime, cause of machine stopping time. Compute OEE contain: Availability, Performance, Quality. This helps manager make appropriate assessments and adjustmets to improve performance.

### FEATURES
- Collect data of runtime, downtime, cause of machine stopping time via OEE controller installed on CNC machines or connect direcly to them.
- Compute A, P, Q, OEE,.. Give statistic charts: OEE, Wasted time, Runtime/Downtime of machine.
- Detailed of summary of machine running, stopping time. Extract data by month and by day
- Auto data collection or Manual data entry.
- Provide APIs for other systems, API security.
- User authentication.
- Manage machine: Add, Update, Delete.
- Access system via Web browser

### CONNECTION DIAGRAM
![overall equipment effectiveness](/assets/oee_diagram.PNG)

- OEE Client connect to OEE Server via RESTful API.
- OEE Client connect to OEE Box, collect cause of machine stoppage via pressesing of operator.
- OEE Client connect to CNC machine, collect runtime, number of tool used, number of tool changed, etc. Protocol is desripted at here:
https://www.haascnc.com/service/troubleshooting-and-how-to/how-to/machine-data-collection---ngc.html

### TABLE STRUCTURE
![overall equipment effectiveness](/assets/table_structue.png)

- machine table: manage Machine
- input table: Collect data of runtime, downtime, etc.
- machine_data_collection table: collect current states of machine (running/stopped), num of tool use, etc.
- account table: user authenticate.

### FAMEWORK USED
- Java Spring Boot, Spring Security JWT, Hibernate, JPA.
- MS SQL Server.

### DEMO
[Watch demo video](./assets/oee_webapp.mp4)
###
https://github.com/user-attachments/assets/688a3032-78ff-4c33-b872-35e01cfb0d6b
