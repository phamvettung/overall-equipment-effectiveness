CREATE DATABASE OEE2025
GO

USE OEE2025
GO


CREATE PROC prDailyOee(@machineId INT, @start date, @end date)
AS
BEGIN	
	SELECT Mac.name AS machine_name, Q3.machine_id, Q3.date, Q3.f0, Q3.f00, Q3.f1, Q3.f2, Q3.f3, Q3.f4, Q3.f5, Q3.f6, Q3.f7, Q3.f8, Q3.f9, Q3.f10,
	Q3.downtime,
	Q3.runtime,
	ISNULL(((Q3.f0 - Q3.f00)*100)/Q3.f0, 0) AS a,
	ISNULL(((Q3.f0 - Q3.f00 - Q3.downtime)*100)/(Q3.f0 - Q3.f00 + 0.01), 0) AS p,
	0 AS q,
	ISNULL((    ISNULL(((Q3.f0 - Q3.f00)*100)/Q3.f0, 0)*ISNULL(((Q3.f0 - Q3.f00 - Q3.downtime)*100)/(Q3.f0 - Q3.f00 + 0.01), 0)   )/100, 0) AS oee
	FROM(
		SELECT *,
		(Q2.f1 + Q2.f2 + Q2.f3 + Q2.f4 + Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 + Q2.f9 + Q2.f10) AS downtime,
		(Q2.f0 - (Q2.f1 + Q2.f2 + Q2.f3 + Q2.f4 + Q2.f5 + Q2.f6 + Q2.f7 + Q2.f8 + Q2.f9 + Q2.f10 + Q2.f00)) AS runtime
		FROM(	
			SELECT Q1.machine_id, Q1.date,
			ISNULL(NULLIF(SUM(Q1.f0),0),450) AS f0, /*Mặc định = 450p nếu ngày hôm đó không thu thập được dữ liệu*/
			SUM(Q1.f00) AS f00,
			SUM(Q1.f1) AS f1,
			SUM(Q1.f2) AS f2,
			SUM(Q1.f3) AS f3,
			SUM(Q1.f4) AS f4,
			SUM(Q1.f5) AS f5,
			SUM(Q1.f6) AS f6,
			SUM(Q1.f7) AS f7,
			SUM(Q1.f8) AS f8,
			SUM(Q1.f9) AS f9,
			SUM(Q1.f10) AS f10
			FROM(
				SELECT Q0.machine_id, Q0.date, 
				ISNULL(Q0.f0, 0) AS f0, 
				ISNULL(Q0.f00, 0) AS f00, 
				ISNULL(Q0.f1, 0) AS f1, 
				ISNULL(Q0.f2, 0) AS f2, 
				ISNULL(Q0.f3, 0) AS f3, 
				ISNULL(Q0.f4, 0) AS f4, 
				ISNULL(Q0.f5, 0) AS f5, 
				ISNULL(Q0.f6, 0) AS f6, 
				ISNULL(Q0.f7, 0) AS f7, 
				ISNULL(Q0.f8, 0) AS f8, 
				ISNULL(Q0.f9, 0) AS f9, 
				ISNULL(Q0.f10, 0) AS f10
				FROM (
					SELECT * FROM input
					PIVOT
					(SUM(input.value) FOR input.downtime_type IN (f0, f00, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10)) AS PivotTable
					where machine_id = @machineId and date >= @start and date <= @end
				)Q0
			)Q1 GROUP BY Q1.machine_id, Q1.date
		)Q2
	)Q3 INNER JOIN machine as Mac on Mac.id = Q3.machine_id
END
GO




CREATE PROC prMonthlyOee(@start date, @end date)
AS
BEGIN

DECLARE @dn int
DECLARE @dcn int
SELECT @dn = DATEDIFF(day, @start, @end)+1
SELECT @dcn = DATEDIFF(week, @start, @end)

	SELECT Mac.name AS machine_name, Q5.machine_id, 
	ROUND(Q5.f0, 1) AS f0, ROUND(Q5.f00, 1) AS f00, ROUND(Q5.f1, 1) AS f1, ROUND(Q5.f2, 1) AS f2, ROUND(Q5.f3, 1) AS f3, ROUND(Q5.f4, 1) AS f4,
	ROUND(Q5.f5, 1) AS f5, ROUND(Q5.f6, 1) AS f6, ROUND(Q5.f7, 1) AS f7, ROUND(Q5.f8, 1) AS f8, ROUND(Q5.f9, 1) AS f9, ROUND(Q5.f10, 1) AS f10,
	Q5.din,
	Q5.downtime,
	Q5.runtime,
	ROUND(Q5.a, 1) AS a,
	ROUND(Q5.p, 1) AS p,
	ROUND(Q5.q, 1) AS q,
	ROUND(( (Q5.a/100)*(Q5.p/100)*(Q5.q/100))*100, 1) AS oee
	FROM(
		SELECT *,
		(( Q4.f0 + (@dn - (Q4.din + @dcn))*450 - Q4.f00 + (@dn - (Q4.din + @dcn))*0 )/( Q4.f0 + (@dn - (Q4.din + @dcn))*450 + 0.01))*100 as a,
		(( Q4.f0 + (@dn - (Q4.din + @dcn))*450 - Q4.f00 + (@dn - (Q4.din + @dcn))*0 - Q4.downtime )/( Q4.f0 + (@dn - (Q4.din + @dcn))*450 - Q4.f00 + (@dn - (Q4.din + @dcn))*0 + 0.01))*100 as p,
		(  (Q4.runtime/Q4.f0)*100  ) AS q
		--1 AS oee
		FROM(
			SELECT *,
			(Q3.f1 + Q3.f2 + Q3.f3 + Q3.f4 + Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8 + Q3.f9 + Q3.f10) AS downtime,
			(Q3.f0 - (Q3.f1 + Q3.f2 + Q3.f3 + Q3.f4 + Q3.f5 + Q3.f6 + Q3.f7 + Q3.f8 + Q3.f9 + Q3.f10 + Q3.f00)) AS runtime
			FROM(
				SELECT Q2.machine_id,
				ISNULL(NULLIF(SUM(Q2.f0),0),450) AS f0,
				SUM(Q2.f00) AS f00,
				SUM(Q2.f1) AS f1,
				SUM(Q2.f2) AS f2,
				SUM(Q2.f3) AS f3,
				SUM(Q2.f4) AS f4,
				SUM(Q2.f5) AS f5,
				SUM(Q2.f6) AS f6,
				SUM(Q2.f7) AS f7,
				SUM(Q2.f8) AS f8,
				SUM(Q2.f9) AS f9,
				SUM(Q2.f10) AS f10,
				COUNT(Q2.machine_id) AS din
				FROM(
					SELECT Q1.machine_id, Q1.date,
					ISNULL(NULLIF(SUM(Q1.f0),0),450) AS f0, /*Mặc định = 450p nếu ngày hôm đó không thu thập được dữ liệu*/
					SUM(Q1.f00) AS f00,
					SUM(Q1.f1) AS f1,
					SUM(Q1.f2) AS f2,
					SUM(Q1.f3) AS f3,
					SUM(Q1.f4) AS f4,
					SUM(Q1.f5) AS f5,
					SUM(Q1.f6) AS f6,
					SUM(Q1.f7) AS f7,
					SUM(Q1.f8) AS f8,
					SUM(Q1.f9) AS f9,
					SUM(Q1.f10) AS f10
					FROM(
						SELECT Q0.machine_id, Q0.date,
						ISNULL(Q0.f0, 0) AS f0, 
						ISNULL(Q0.f00, 0) AS f00, 
						ISNULL(Q0.f1, 0) AS f1, 
						ISNULL(Q0.f2, 0) AS f2, 
						ISNULL(Q0.f3, 0) AS f3, 
						ISNULL(Q0.f4, 0) AS f4, 
						ISNULL(Q0.f5, 0) AS f5, 
						ISNULL(Q0.f6, 0) AS f6, 
						ISNULL(Q0.f7, 0) AS f7, 
						ISNULL(Q0.f8, 0) AS f8, 
						ISNULL(Q0.f9, 0) AS f9, 
						ISNULL(Q0.f10, 0) AS f10
						FROM (
							SELECT * FROM input
							PIVOT
							(SUM(input.value) FOR input.downtime_type IN (f0, f00, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10)) AS PivotTable
							where date >= @start and date <= @end
						)Q0
					)Q1 GROUP BY Q1.machine_id, Q1.date
				)Q2 GROUP BY Q2.machine_id
			)Q3
		)Q4 
	)Q5 INNER JOIN machine as Mac on Mac.id = Q5.machine_id
END
GO





CREATE PROC prMachineDataCollection (@start date, @end date)
AS
BEGIN

SELECT Mac.name AS machine_name, T2.machine_id,
ROUND(T2.Q200, 1) AS Q200,
ROUND(T2.Q201, 1) AS Q201,
ROUND(T2.Q300, 1) AS Q300,
ROUND(T2.Q301, 1) AS Q301,
ROUND(T2.Q500, 1) AS Q500
FROM(
	SELECT T1.machine_id,
	SUM(T1.Q200) AS Q200,
	SUM(T1.Q201) AS Q201,
	SUM(T1.Q300) AS Q300,
	SUM(T1.Q301) AS Q301,
	SUM(T1.Q500) AS Q500
	FROM (
		SELECT T0.machine_id, T0.date, 
		ISNULL(T0.Q200, 0) AS Q200, 
		ISNULL(T0.Q201, 0) AS Q201, 
		ISNULL(T0.Q300, 0) AS Q300, 
		ISNULL(T0.Q301, 0) AS Q301,
		ISNULL(T0.Q500, 0) AS Q500
		FROM(
			SELECT * FROM machine_data_collection
			PIVOT
			(SUM(machine_data_collection.value) FOR machine_data_collection.qcommand IN (Q200, Q201, Q300, Q301, Q500)) AS PivotTable
			WHERE date >= @start and date <= @end
		)T0
	)T1 GROUP BY T1.machine_id
)T2 INNER JOIN machine as Mac on Mac.id = T2.machine_id

END
GO