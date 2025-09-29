package vn.intech.oee2025.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.intech.oee2025.entity.Input;

@Repository
public interface InputRepository extends JpaRepository<Input, Long>{
	@Query(value="select * from input where date= :d and downtime_type = :type and machine_id = :mid", nativeQuery = true)
	List<Input> findByDateAndTypeAndMid(Date d, String type, int mid);
}
