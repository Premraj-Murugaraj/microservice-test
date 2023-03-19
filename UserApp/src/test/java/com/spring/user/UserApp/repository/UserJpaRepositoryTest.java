package com.spring.user.UserApp.repository;

import com.spring.user.UserApp.model.User;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.repository.UserJPARepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
@DataJpaTest
public class UserJpaRepositoryTest {

    @Autowired
    UserJPARepository userJPARepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void shouldGetAllUserTest(){
        //arrange
        Users user1 = Users.builder().name("Megha").dob(LocalDate.now().minusYears(10)).build();
        //userJPARepository.save(user1);
        testEntityManager.persistAndFlush(user1);

        //act
        List<Users> allUser = userJPARepository.findAll();

        //assert
        Assertions.assertEquals(4, allUser.size());
    }

    @Test
    void shouldGetAUser(){
        //arrange
        Users user1 = Users.builder().name("Dhiya").dob(LocalDate.now().minusYears(10)).build();
        userJPARepository.save(user1);
        Users user2 = userJPARepository.findById(user1.getId()).get();
        Assertions.assertEquals(user2.getName(),"Dhiya");
    }

    @Test
    void shouldUpdateUserTest(){
        Users user1 = Users.builder().name("Dhiya").dob(LocalDate.now().minusYears(10)).build();
        Users savedUser = userJPARepository.save(user1);
        Users userToEdit = userJPARepository.findById(savedUser.getId()).get();

        userToEdit.setName("Dharshini");
        userToEdit.setDob(LocalDate.now().minusYears(20));

        Users updatedUser = userJPARepository.save(userToEdit);

        Assertions.assertEquals("Dharshini", updatedUser.getName());
        Assertions.assertEquals(LocalDate.now().minusYears(20),updatedUser.getDob());
    }

    @Test
    void shouldDeleteUserTest(){
        //arrange
        Users user1 = Users.builder().name("Madhu").dob(LocalDate.now().minusYears(10)).build();
        userJPARepository.save(user1);

        userJPARepository.deleteById(user1.getId());

        //act
        List<Users> allUser = userJPARepository.findAll();
        //assert
        Assertions.assertEquals(3, allUser.size());

    }
}
