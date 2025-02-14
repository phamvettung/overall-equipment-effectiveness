create database OEE
go
use OEE
go

create table LossTypes(
	id varchar(10) primary key,
	name nvarchar(50) not null
)


create table Factors(
	id varchar(10) primary key,
	idType varchar(10) not null,
	name nvarchar(500) not null,
	describe nvarchar(1000),
	CONSTRAINT FK_idType FOREIGN KEY (idType) REFERENCES LossTypes(id) ON DELETE CASCADE ON UPDATE CASCADE
)


select * from Factors

create table Machines(
	id varchar(10) primary key,
	name nvarchar(500) not null,
	describe nvarchar(1000)
)

create table Inputs(
	id INT IDENTITY (1, 1) PRIMARY KEY,
	idFactor varchar(10) not null,
	idMachine varchar(10) not null,
	value float not null,
	date date not null,
	time time not null,
	unit nvarchar(50),
	userName nvarchar(50),
	CONSTRAINT FK_idFactor FOREIGN KEY (idFactor) REFERENCES Factors(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_idMachine FOREIGN KEY (idMachine) REFERENCES Machines(id) ON DELETE CASCADE ON UPDATE CASCADE
)

select * from Inputs where date >= '2024-12-20'

ALTER TABLE Inputs
  ADD id INT IDENTITY (1, 1) PRIMARY KEY;

ALTER TABLE Inputs
  ALTER COLUMN time time not null;

DROP TABLE Inputs 
GO

-- TRIGGER
ALTER TRIGGER tgDeleteLossTypes
ON LossTypes
FOR Delete
AS
BEGIN
	ROLLBACK TRAN
	PRINT 'Can not delete'
END
GO

DROP TRIGGER tgDeleteLossTypes
GO

CREATE TRIGGER tgDeleteFactors
ON Factors
FOR Delete
AS
BEGIN
	ROLLBACK TRAN
	PRINT 'Can not delete'
END
GO

DROP TRIGGER tgDeleteFactors
GO


-- PROCS (Không dùng)
Alter Proc prGetOeeTable(@idMachine varchar(10), @start date, @end date)
as
begin
	select M.name, Q3.idMachine, Q3.date, Q3.f1, Q3.f2, Q3.f3, Q3.f4, Q3.f5, Q3.f6, Q3.f7, Q3.f8, Q3.f9
	A,P,Q, ISNULL(((Q3.A/100)*(Q3.P/100)*(Q3.Q/100))*100, 0) as OEE
	from(
		-- Q2: downTime, A, P, Q
		select * , Q2.f1 + Q2.f2 + Q2.f3 + Q2.f4 + Q2.f5 + Q2.f6 + Q2.f7+ Q2.f8 as downTime, 
		ISNULL(((450-(Q2.f1 + Q2.f2 + Q2.f3 + Q2.f4 + Q2.f5 + Q2.f6 + Q2.f7+ Q2.f8))*100)/450, 0) as A,
		ISNULL(((Q2.f9 - Q2.f4)*100)/NULLIF(Q2.f9, 0), 0) as P,
		ISNULL(((Q2.f9 - Q2.f2)*100)/NULLIF(Q2.f9, 0), 0) as Q
		from(
			-- Q1: sum Inputs
			select Q1.idMachine, Q1.date, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5, SUM(Q1.f6) as f6,
			SUM(Q1.f7) as f7, SUM(Q1.f8) as f8, SUM(Q1.f9) as f9
			from(
				-- Q: convert Inputs
				Select Q.idMachine, Q.date, Q.unit, ISNULL(Q.f1, 0) as f1, ISNULL(Q.f2, 0) as f2, ISNULL(Q.f3, 0) as f3, ISNULL(Q.f4, 0) as f4,
				ISNULL(Q.f5, 0) as f5, ISNULL(Q.f6, 0) as f6, ISNULL(Q.f7, 0) as f7, ISNULL(Q.f8, 0) as f8, ISNULL(Q.f9, 0) as f9 
				from (
					SELECT * FROM Inputs
					PIVOT
					(SUM(Inputs.value) FOR Inputs.idFactor IN (f1,f2, f3, f4, f5, f6, f7, f8, f9)) AS PivotTable
					where idMachine = @idMachine and date >= @start and date <= @end
				)Q
			)Q1
			group by Q1.idMachine, Q1.date			
		)Q2
	)Q3
	inner join Machines as M on M.id = Q3.idMachine
end
go




EXEC prGetOeeTable 450, 'CNC1', '2024-05-01', '2024-05-31'
---------------------- MAIN -----------------------------------------
ALTER PROC prGetOeeTable(@totalProductionTime integer, @idMachine varchar(10), @start date, @end date)
AS
BEGIN
	--Q3: Oee
	select M.name, Q3.idMachine, Q3.date, Q3.f00, Q3.f1, Q3.f2, Q3.f3, Q3.f4, Q3.f5, Q3.f6, Q3.f7, Q3.f8,
	A,P,Q, ISNULL(((Q3.A/100)*(Q3.P/100))*100, 0) as OEE, Q3.downTime as 'DownTime'
	from(
		-- Q2: downTime, A, P, Q
		select *, Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4+ Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 as downTime, 
		ISNULL(((@totalProductionTime - Q2.f00)*100)/@totalProductionTime, 0) as A,
		ISNULL(((@totalProductionTime - Q2.f00 - (Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4+ Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8))*100)/(0.1 + @totalProductionTime - Q2.f00), 0) as P,
		0 as Q
		from(
			-- Q1: sum factor
			select Q1.idMachine, Q1.date, SUM(Q1.f00) as f00, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5
			, SUM(Q1.f6) as f6, SUM(Q1.f7) as f7, SUM(Q1.f8) as f8
			from(
				-- Q: convert Inputs
				Select Q.idMachine, Q.date, Q.unit, ISNULL(Q.f00, 0) as f00, ISNULL(Q.f1, 0) as f1, ISNULL(Q.f2, 0) as f2, ISNULL(Q.f3, 0) as f3, ISNULL(Q.f4, 0) as f4,
				ISNULL(Q.f5, 0) as f5, ISNULL(Q.f6, 0) as f6, ISNULL(Q.f7, 0) as f7, ISNULL(Q.f8, 0) as f8
				from (
					SELECT * FROM Inputs
					PIVOT
					(SUM(Inputs.value) FOR Inputs.idFactor IN (f00, f1, f2, f3, f4, f5, f6, f7, f8)) AS PivotTable
					where idMachine = @idMachine and date >= @start and date <= @end
				)Q
			)Q1
			group by Q1.idMachine, Q1.date			
		)Q2
	)Q3
	inner join Machines as M on M.id = Q3.idMachine
END
GO









EXEC prGetTotalDownTime '2024-02-29', 'CNC1'
as
begin
ALTER PROC prGetTotalDownTime(@date date, @idMachine varchar(10))
AS
BEGIN
Select 
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f1' and date = @date and idMachine = @idMachine) as 'f1', 
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f2' and date = @date and idMachine = @idMachine) as 'f2',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f3' and date = @date and idMachine = @idMachine) as 'f3',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f4' and date = @date and idMachine = @idMachine) as 'f4',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f5' and date = @date and idMachine = @idMachine) as 'f5',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f6' and date = @date and idMachine = @idMachine) as 'f6',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f7' and date = @date and idMachine = @idMachine) as 'f7',
(Select ISNULL(SUM(value), 0) from Inputs where idFactor = 'f8' and date = @date and idMachine = @idMachine) as 'f8'
END


-- HÀM TÍNH OEE THÁNG THEO CÔNG THỨC CŨ--
EXEC prGetOeeTrend 450, '2024-06-01', '2024-06-30'
ALTER PROC prGetOeeTrend (@totalProductionTime integer, @start date, @end date)
AS
BEGIN
	select M.name, Q3.idMachine, Q3.f00, Q3.f1, Q3.f2, Q3.f3, Q3.f4, Q3.f5, Q3.f6, Q3.f7, Q3.f8,
	A,P,Q, ISNULL(((Q3.A/100)*(Q3.P/100))*100, 0) as OEE, Q3.downTime as 'DownTime'
	from(
		select *, Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4+ Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 as downTime, 
		ISNULL(((@totalProductionTime*(DATEDIFF(day, @start, @end) - DATEDIFF(week, @start, @end)) - Q2.f00)*100)/(@totalProductionTime*(DATEDIFF(day, @start, @end) - DATEDIFF(week, @start, @end))), 0) as A,
		ISNULL(((@totalProductionTime*(DATEDIFF(day, @start, @end) - DATEDIFF(week, @start, @end)) - Q2.f00 - (Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4+ Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8))*100)/(@totalProductionTime*(DATEDIFF(day, @start, @end) - DATEDIFF(week, @start, @end)) - Q2.f00), 0) as P,
		100 as Q
		from(
			select Q1.idMachine, SUM(Q1.f00) as f00, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5
			, SUM(Q1.f6) as f6, SUM(Q1.f7) as f7, SUM(Q1.f8) as f8
				from(
					Select Q.idMachine, Q.date, Q.unit, ISNULL(Q.f00, 0) as f00, ISNULL(Q.f1, 0) as f1, ISNULL(Q.f2, 0) as f2, ISNULL(Q.f3, 0) as f3, ISNULL(Q.f4, 0) as f4,
					ISNULL(Q.f5, 0) as f5, ISNULL(Q.f6, 0) as f6, ISNULL(Q.f7, 0) as f7, ISNULL(Q.f8, 0) as f8
					from(
						SELECT * FROM Inputs
						PIVOT
						(SUM(Inputs.value) FOR Inputs.idFactor IN (f00, f1, f2, f3, f4, f5, f6, f7, f8)) AS PivotTable
						where date >= @start and date <=  @end
					)Q
			)Q1
			group by Q1.idMachine
		)Q2
	)Q3
	inner join Machines as M on M.id = Q3.idMachine;
END
GO



-- HÀM TÍNH OEE THÁNG THEO CÔNG THỨC MỚI 28-06 --
EXEC prGetOeeTrend 450, '2024-06-01', '2024-06-30'

ALTER PROC prGetOeeTrend (@totalProductionTime integer, @start date, @end date)
AS
BEGIN
select M.name, Q4.idMachine, (Q4.f00 + (DATEDIFF(day, @start, @end)+1 - (Q4.din+DATEDIFF(week, @start, @end)))*0) as f00, Q4.f1, Q4.f2, Q4.f3, Q4.f4, Q4.f5, Q4.f6, Q4.f7, Q4.f8,
A,P,Q, ISNULL(((Q4.A/100)*(Q4.P/100))*100, 0) as OEE, Q4.downTime as 'DownTime', @totalProductionTime*Q4.din - Q4.downTime as 'RunTime'
from(
	select *, Q3.f1 + Q3.f2+ Q3.f3+ Q3.f4+ Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8 as downTime, 
	ISNULL(((@totalProductionTime*(DATEDIFF(day, @start, @end)+1 - DATEDIFF(week, @start, @end)) - (Q3.f00 + (DATEDIFF(day, @start, @end)+1 - (Q3.din+DATEDIFF(week, @start, @end)))*0))/(@totalProductionTime*(DATEDIFF(day, @start, @end)+1 - DATEDIFF(week, @start, @end))))*100, 0) as A,
	ISNULL(((@totalProductionTime*(DATEDIFF(day, @start, @end)+1 - DATEDIFF(week, @start, @end)) - (Q3.f00 + (DATEDIFF(day, @start, @end)+1 - (Q3.din+DATEDIFF(week, @start, @end)))*0) - (Q3.f1 + Q3.f2+ Q3.f3+ Q3.f4+ Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8))*100)/(@totalProductionTime*(DATEDIFF(day, @start, @end)+1 - DATEDIFF(week, @start, @end)) - (Q3.f00 + (DATEDIFF(day, @start, @end)+1 - (Q3.din+DATEDIFF(week, @start, @end)))*0)), 0) as P,
	0 as Q
	from(
		select Q2.idMachine, SUM(Q2.f00) as f00, SUM(Q2.f1) as f1, SUM(Q2.f2) as f2, SUM(Q2.f3) as f3, SUM(Q2.f4) as f4, SUM(Q2.f5) as f5
		, SUM(Q2.f6) as f6, SUM(Q2.f7) as f7, SUM(Q2.f8) as f8, COUNT(Q2.idMachine)  as 'din'
		from (
			select Q1.idMachine, SUM(Q1.f00) as f00, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5
			, SUM(Q1.f6) as f6, SUM(Q1.f7) as f7, SUM(Q1.f8) as f8, COUNT(Q1.date) as 'countDate'
				from(
					Select Q0.idMachine, Q0.date, ISNULL(Q0.f00, 0) as f00, ISNULL(Q0.f1, 0) as f1, ISNULL(Q0.f2, 0) as f2, ISNULL(Q0.f3, 0) as f3, ISNULL(Q0.f4, 0) as f4,
					ISNULL(Q0.f5, 0) as f5, ISNULL(Q0.f6, 0) as f6, ISNULL(Q0.f7, 0) as f7, ISNULL(Q0.f8, 0) as f8
					from(
						SELECT * FROM Inputs
						PIVOT
						(SUM(Inputs.value) FOR Inputs.idFactor IN (f00, f1, f2, f3, f4, f5, f6, f7, f8)) AS PivotTable
						where date >= @start and date <=  @end
					)Q0
			)Q1
			group by Q1.idMachine, Q1.date
		)Q2
		group by  Q2.idMachine
	)Q3
)Q4
inner join Machines as M on M.id = Q4.idMachine;
END
GO


-- OEE 2024-07-08
--1. Thêm biểu đồ thống kê Lãng phí dừng của tất cả các máy CNC.

--2. Những ngày ko có dữ liệu: TG dừng ko đơn hàng = 0, OEE = 100%.

--3. Thêm 2 lý do dừng máy: Dừng do không có nhân viên vận hành, Dừng họp hoặc đào tạo.
insert into Factors values ('f9', 'l2', N'Dừng do không có nhân viên vận hành', '')
insert into Factors values ('f10', 'l1', N'Dừng họp hoặc đào tạo', '')

update Factors set name = N'Tổng thời gian sản xuất' where id = 'f0'
update Factors set name = N'Dừng chuẩn bị : chưa có chương trình' where id = 'f1'
update Factors set name = N'Dừng sửa NG, làm lại hàng NG' where id = 'f2'
update Factors set name = N'Dừng sửa chương trình, đồ gá' where id = 'f3'
update Factors set name = N'Dừng chờ phôi' where id = 'f4'
update Factors set name = N'Dừng chờ dụng cụ' where id = 'f5'
update Factors set name = N'Dừng do máy hỏng đột xuất' where id = 'f6'
update Factors set name = N'Dừng chờ QC kiểm tra' where id = 'f7'
update Factors set name = N'Dừng đầu giờ, giải lao, cuối giờ' where id = 'f8'

select * from Factors


--4. Có thể thay đổi Thời gian sản xuất theo ngày (không cố định 450p).
EXEC prGetOeeTable 'CNC1', '2024-05-01', '2024-05-31'
----------------------MAIN (dùng)-----------------------------------------
ALTER PROC prGetOeeTable(@idMachine varchar(10), @start date, @end date)
AS
BEGIN
	--Q3: Oee
	select M.name, Q3.idMachine, Q3.date, Q3.f0, Q3.f00, Q3.f1, Q3.f2, Q3.f3, Q3.f4, Q3.f5, Q3.f6, Q3.f7, Q3.f8, Q3.f9, Q3.f10,
	A,P,Q, ISNULL(((Q3.A/100)*(Q3.P/100))*100, 0) as OEE, Q3.downTime as 'DownTime', Q3.f0 - Q3.downTime as 'RunTime'
	from(
		-- Q2: downTime, A, P, Q
		select *, Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4+ Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 + Q2.f9 + Q2.f10 as downTime, 
		ISNULL(((Q2.f0 - Q2.f00)*100)/Q2.f0, 0) as A,
		ISNULL(((Q2.f0 - Q2.f00 - (Q2.f1 + Q2.f2+ Q2.f3+ Q2.f4 + Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 + Q2.f9 + Q2.f10))*100)/(Q2.f0 - Q2.f00 + 0.01), 0) as P,
		0 as Q
		from(
			-- Q1: sum factor
			select Q1.idMachine, Q1.date, ISNULL(NULLIF(SUM(Q1.f0),0),450) as f0, SUM(Q1.f00) as f00, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5
			, SUM(Q1.f6) as f6, SUM(Q1.f7) as f7, SUM(Q1.f8) as f8, SUM(Q1.f9) as f9, SUM(Q1.f10) as f10
			from(
				-- Q0: convert Inputs
				Select Q0.idMachine, Q0.date, ISNULL(Q0.f0, 0) as f0, ISNULL(Q0.f00, 0) as f00, ISNULL(Q0.f1, 0) as f1, ISNULL(Q0.f2, 0) as f2, ISNULL(Q0.f3, 0) as f3, ISNULL(Q0.f4, 0) as f4,
				ISNULL(Q0.f5, 0) as f5, ISNULL(Q0.f6, 0) as f6, ISNULL(Q0.f7, 0) as f7, ISNULL(Q0.f8, 0) as f8, ISNULL(Q0.f9, 0) as f9, ISNULL(Q0.f10, 0) as f10
				from (
					SELECT * FROM Inputs
					PIVOT
					(SUM(Inputs.value) FOR Inputs.idFactor IN (f0, f00, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10)) AS PivotTable
					where idMachine = @idMachine and date >= @start and date <= @end
				)Q0
			)Q1
			group by Q1.idMachine, Q1.date
		)Q2
	)Q3
	inner join Machines as M on M.id = Q3.idMachine
END
GO


insert into Inputs values ('f0', 'CNC1', 380, '2024-05-02', '08:48:00', 'phút', 'admin')
insert into Inputs values ('f0', 'CNC1', 10, '2024-05-02', '08:48:00', 'phút', 'admin')
insert into Inputs values ('f1', 'CNC1', 60, '2024-05-02', '08:49:00', 'phút', 'admin')
insert into Inputs values ('f1', 'CNC1', 30, '2024-05-02', '08:50:00', 'phút', 'admin')
insert into Inputs values ('f1', 'CNC1', 10, '2024-05-02', '08:50:00', 'phút', 'admin')


-- HÀM TÍNH OEE THÁNG THEO CÔNG THỨC MỚI 09/07 --
EXEC prGetOeeTrend '2024-06-01', '2024-06-30'

ALTER PROC prGetOeeTrend (@start date, @end date)
AS
BEGIN

DECLARE @dn int
DECLARE @dcn int
SELECT @dn = DATEDIFF(day, @start, @end)+1
SELECT @dcn = DATEDIFF(week, @start, @end)
select M.name as 'name', Q4.idMachine as 'idMachine', (Q4.f0 + (@dn - (Q4.din + @dcn))*450) AS f0, (Q4.f00 + (@dn - (Q4.din + @dcn)*0)) as f00, Q4.f1, Q4.f2, Q4.f3, Q4.f4, Q4.f5, Q4.f6, Q4.f7, Q4.f8, Q4.f9, Q4.f10,
Q4.din, A, P, Q, ((Q4.A/100)*(Q4.P/100))*100 as OEE, Q4.downTime as 'DownTime', (Q4.f0 + (@dn - (Q4.din + @dcn))*450) - (Q4.downTime + Q4.f00 + (@dn - (Q4.din + @dcn)*0)) as 'RunTime'
from(
	select *, Q3.f1 + Q3.f2 + Q3.f3 + Q3.f4 + Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8 + Q3.f9 + Q3.f10 as downTime,															
	(( Q3.f0 + (@dn - (Q3.din + @dcn))*450 - Q3.f00 + (@dn - (Q3.din + @dcn))*0 )/( Q3.f0 + (@dn - (Q3.din + @dcn))*450 ))*100 as A,
	(( Q3.f0 + (@dn - (Q3.din + @dcn))*450 - Q3.f00 + (@dn - (Q3.din + @dcn))*0 - (Q3.f1 + Q3.f2+ Q3.f3+ Q3.f4+ Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8 + Q3.f9 + Q3.f10) )/( Q3.f0 + (@dn - (Q3.din + @dcn))*450 - Q3.f00 + (@dn - (Q3.din + @dcn))*0 ))*100 as P,
	0 as Q
	from(
		select Q2.idMachine, ISNULL(NULLIF(SUM(Q2.f0),0),450) as f0, SUM(Q2.f00) as f00, SUM(Q2.f1) as f1, SUM(Q2.f2) as f2, SUM(Q2.f3) as f3, SUM(Q2.f4) as f4, SUM(Q2.f5) as f5
		, SUM(Q2.f6) as f6, SUM(Q2.f7) as f7, SUM(Q2.f8) as f8, SUM(Q2.f9) as f9, SUM(Q2.f10) as f10, COUNT(Q2.idMachine)  as 'din'
		from (
			select Q1.idMachine, ISNULL(NULLIF(SUM(Q1.f0),0),450) as f0, SUM(Q1.f00) as f00, SUM(Q1.f1) as f1, SUM(Q1.f2) as f2, SUM(Q1.f3) as f3, SUM(Q1.f4) as f4, SUM(Q1.f5) as f5
			, SUM(Q1.f6) as f6, SUM(Q1.f7) as f7, SUM(Q1.f8) as f8, SUM(Q1.f9) as f9, SUM(Q1.f10) as f10, COUNT(Q1.date) as 'din'
				from(
					Select Q0.idMachine, Q0.date, ISNULL(Q0.f0, 0) as f0, ISNULL(Q0.f00, 0) as f00, ISNULL(Q0.f1, 0) as f1, ISNULL(Q0.f2, 0) as f2, ISNULL(Q0.f3, 0) as f3, ISNULL(Q0.f4, 0) as f4,
					ISNULL(Q0.f5, 0) as f5, ISNULL(Q0.f6, 0) as f6, ISNULL(Q0.f7, 0) as f7, ISNULL(Q0.f8, 0) as f8, ISNULL(Q0.f9, 0) as f9, ISNULL(Q0.f10, 0) as f10
					from(
						SELECT * FROM Inputs
						PIVOT
						(SUM(Inputs.value) FOR Inputs.idFactor IN (f0, f00, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10)) AS PivotTable
						where date >= @start and date <=  @end
					)Q0
			)Q1
			group by Q1.idMachine, Q1.date
		)Q2
		group by  Q2.idMachine
	)Q3
)Q4
inner join Machines as M on M.id = Q4.idMachine
END
GO

