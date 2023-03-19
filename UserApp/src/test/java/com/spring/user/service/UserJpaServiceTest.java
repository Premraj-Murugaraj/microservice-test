package com.spring.user.service;

import com.spring.user.UserApp.exception.UserNotFoundException;
import com.spring.user.UserApp.model.Users;
import com.spring.user.UserApp.repository.UserJPARepository;
import com.spring.user.UserApp.service.UserJPAService;
import com.spring.user.UserApp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class UserJpaServiceTest {

    @Mock
    UserJPARepository userJPARepository;

    @InjectMocks
    UserJPAService userJPAService;

    @Test
    void get_All_Users_test(){
        List<Users> users =
                Arrays.asList(Users.builder().id(1).name("Mega").dob(LocalDate.now().minusYears(10)).build(),
                        Users.builder().id(1).name("Nandhini").dob(LocalDate.now().minusYears(10)).build());

        Mockito.when(userJPARepository.findAll()).thenReturn(users);
        //act
        List<Users> usersList = userJPAService.retriveAllUsers();

        //assert
        Assertions.assertEquals(2,usersList.size());
        Assertions.assertTrue(usersList.stream().anyMatch(x->x.getName().contains("Mega")));
    }

    @Test
    void get_specific_user_test(){
        Users user = Users.builder().id(1).name("Kundhavai").dob(LocalDate.now().minusYears(10)).build();
        Mockito.when(userJPARepository.findById(anyInt())).thenReturn(Optional.of(user));

        //act
        Users updateuser = userJPAService.getAUser(10);

        Assertions.assertEquals("Kundhavai",updateuser.getName());
    }

    @Test
    void another_get_list_user(){
        Users dbUser = Users.builder().id(1).name("Nandhini").dob(LocalDate.now().minusYears(30)).build();
        Users payLoadUser = Users.builder().id(1).name("Kundhavai").dob(LocalDate.now().minusYears(10)).build();
        Mockito.when(userJPARepository.findById(anyInt()))
                .thenReturn(Optional.of(dbUser))
                .thenReturn(Optional.of(payLoadUser));

        Users user1 = userJPAService.getAUser(100);

        Assertions.assertEquals("Nandhini",user1.getName());

        Users user2 = userJPAService.getAUser(200);

        Assertions.assertEquals("Kundhavai",user2.getName());

    }

    @Test
    void delete_user_test(){
        userJPAService.deleteUser(1);
        Mockito.verify(userJPARepository, Mockito.times(1)).deleteById(anyInt());
    }

    @Test
    void update_user_test(){
       Users dbUser = Users.builder().id(1).name("Nandhini").dob(LocalDate.now().minusYears(30)).build();
        Users payLoadUser = Users.builder().id(1).name("Kundhavai").dob(LocalDate.now().minusYears(10)).build();
        //find user in DB mock
        Mockito.when(userJPARepository.findById(anyInt())).thenReturn(Optional.of(dbUser));

        //manipulate attribute
        Users updateUser = userJPAService.updateUser(payLoadUser);

        Assertions.assertEquals("Kundhavai",updateUser.getName());
        Assertions.assertEquals(LocalDate.now().minusYears(10),updateUser.getDob());

        InOrder inOrder = Mockito.inOrder(userJPARepository);
        inOrder.verify(userJPARepository).findById(1);
        inOrder.verify(userJPARepository).save(dbUser);
    }

    @Test
    void should_throw_exception_test(){
        Users dbUser = Users.builder().id(1).name("Nandhini").dob(LocalDate.now().minusYears(30)).build();
        Mockito.when(userJPARepository.findById(anyInt())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class,() -> userJPAService.updateUser(dbUser));
    }



}
