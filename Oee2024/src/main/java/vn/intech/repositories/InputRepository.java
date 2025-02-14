package vn.intech.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import vn.intech.entity.Inputs;

public interface InputRepository extends JpaRepository<Inputs, String>{
	@Query(value="select *  from Inputs where date= :d and idFactor = :fid and idMachine = :mid", nativeQuery = true)
	List<Inputs> findByDateAndFidAndMid(Date d, String fid, String mid);
	
	@Query(value="SELECT MAX(date) FROM Inputs WHERE idMachine = :mid", nativeQuery = true)
	Date getMaxDate(String mid);
}
