package com.spring.user.UserApp.repository;

import com.spring.user.UserApp.model.Car;
import com.spring.user.UserApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<Users,Integer> {
}
