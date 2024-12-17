package tech.reliab.course.pyatkovnsLab.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.pyatkovnsLab.bank.PyatkovnsLabApplication;
import tech.reliab.course.pyatkovnsLab.bank.entity.User;
import tech.reliab.course.pyatkovnsLab.bank.model.UserRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.UserServiceImpl;
import tech.reliab.course.pyatkovnsLab.container.PostgreSQLContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = PyatkovnsLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {PostgreSQLContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleUserServiceImplIntegrationTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setFullName("John Doe");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setJob("Developer");

        User createdUser = userServiceImpl.createUser(request);
        Assertions.assertNotNull(createdUser.getId(), "ID пользователя не должен быть null после создания");
        Assertions.assertEquals("John Doe", createdUser.getFullName(), "Имя должно совпадать");
        Assertions.assertEquals("Developer", createdUser.getJob(), "Работа должна совпадать");
    }

    @Test
    void testUpdateUser() {
        UserRequest request = new UserRequest();
        request.setFullName("Jane Smith");
        request.setBirthDate(LocalDate.of(1985, 5, 5));
        request.setJob("Analyst");

        User user = userServiceImpl.createUser(request);
        User updatedUser = userServiceImpl.updateUser(user.getId(), "Jane Johnson");
        Assertions.assertEquals("Jane Johnson", updatedUser.getFullName(), "Имя пользователя должно обновиться");
    }

    @Test
    void testGetUserById() {
        UserRequest request = new UserRequest();
        request.setFullName("Mark Twain");
        request.setBirthDate(LocalDate.of(1970, 7, 7));
        request.setJob("Writer");

        User user = userServiceImpl.createUser(request);
        User foundUser = userServiceImpl.getUserDtoById(user.getId());
        Assertions.assertNotNull(foundUser, "Пользователь должен быть найден по ID");
        Assertions.assertEquals("Mark Twain", foundUser.getFullName(), "Имя найденного пользователя должно совпадать");
    }

    @Test
    void testGetAllUsers() {
        UserRequest request1 = new UserRequest();
        request1.setFullName("User A");
        request1.setBirthDate(LocalDate.of(1992, 3, 3));
        request1.setJob("Tester");
        userServiceImpl.createUser(request1);

        UserRequest request2 = new UserRequest();
        request2.setFullName("User B");
        request2.setBirthDate(LocalDate.of(1988, 4, 4));
        request2.setJob("Designer");
        userServiceImpl.createUser(request2);

        List<User> users = userServiceImpl.getAllUsers();
        Assertions.assertEquals(2, users.size(), "Должно вернуться 2 пользователя");
    }

    @Test
    void testDeleteUser() {
        UserRequest request = new UserRequest();
        request.setFullName("User to Delete");
        request.setBirthDate(LocalDate.of(1993, 6, 6));
        request.setJob("Clerk");

        User user = userServiceImpl.createUser(request);
        userServiceImpl.deleteUser(user.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> userServiceImpl.getUserDtoById(user.getId()),
                "После удаления попытка получить пользователя по ID должна вызывать исключение"
        );
    }
}
