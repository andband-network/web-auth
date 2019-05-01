package com.andband.auth.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    @Modifying
    @Query("update User set enabled = true where accountId = :accountId")
    void enableUserWhereAccountId(String accountId);

}
