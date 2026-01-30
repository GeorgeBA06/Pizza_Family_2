package com.example.pizza.repository;

import com.example.pizza.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @NonNull
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should not be null") String email);
}
