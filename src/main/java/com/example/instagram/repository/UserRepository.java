package com.example.instagram.repository;

import com.example.instagram.Entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<SiteUser,Long> {


    Optional<SiteUser> findByusername(String username);
}
