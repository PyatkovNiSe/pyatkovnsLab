package tech.reliab.course.pyatkovnsLab.bank.service;

import tech.reliab.course.pyatkovnsLab.bank.entity.User;
import tech.reliab.course.pyatkovnsLab.bank.model.UserRequest;

import java.util.List;

public interface UserServiceImpl {
    void requestUserInfo();

    User createUser(UserRequest userRequest);

    void deleteUser(int id);

    User updateUser(int id, String name);

    User getUserDtoById(int id);

    List<User> getAllUsers();
}