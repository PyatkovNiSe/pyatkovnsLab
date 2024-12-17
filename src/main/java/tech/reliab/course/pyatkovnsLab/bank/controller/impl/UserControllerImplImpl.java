package tech.reliab.course.pyatkovnsLab.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.pyatkovnsLab.bank.entity.User;
import tech.reliab.course.pyatkovnsLab.bank.model.UserRequest;
import tech.reliab.course.pyatkovnsLab.bank.service.UserServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImplImpl implements tech.reliab.course.pyatkovnsLab.bank.controller.UserControllerImpl {

    private final UserServiceImpl userServiceImpl;

    @Override
    @PostMapping
    public ResponseEntity<User> createUser(UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(userRequest));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(int id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(int id, String name) {
        return ResponseEntity.ok(userServiceImpl.updateUser(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(int id) {
        return ResponseEntity.ok(userServiceImpl.getUserDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }
}
