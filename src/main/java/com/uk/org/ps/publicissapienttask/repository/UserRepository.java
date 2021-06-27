package com.uk.org.ps.publicissapienttask.repository;

import com.uk.org.ps.publicissapienttask.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

    @Query
    UserModel findByUsername(String username);
}
