package vn.intech.oee2025.service;

import vn.intech.oee2025.entity.Account;

public interface UserSevice {
	Account findByUserName(String userName);
	boolean existsByUsername(String userName);
	boolean existsByEmail(String email);
	Account saveOrUpdate(Account account);
}
