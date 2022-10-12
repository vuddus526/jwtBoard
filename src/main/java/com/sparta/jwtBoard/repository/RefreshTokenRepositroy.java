package com.sparta.jwtBoard.repository;

import com.sparta.jwtBoard.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepositroy extends JpaRepository<RefreshToken, String> {
}
