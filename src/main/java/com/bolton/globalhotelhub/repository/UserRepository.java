package com.bolton.globalhotelhub.repository;

import com.bolton.globalhotelhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    Users findByEmail(String email);

}
