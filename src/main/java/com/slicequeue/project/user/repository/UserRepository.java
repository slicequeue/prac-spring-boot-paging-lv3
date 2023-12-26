package com.slicequeue.project.user.repository;

import com.slicequeue.project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
