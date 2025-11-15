package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    HashMap<Long, User> users = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User updateUser) {

        User oldUser = users.get(updateUser.getId());

        if (updateUser.getName() == null || updateUser.getName().isBlank()) {
            oldUser.setName(updateUser.getLogin());
        } else {
            oldUser.setName(updateUser.getName());
        }

        oldUser.setLogin(updateUser.getLogin());
        oldUser.setEmail(updateUser.getEmail());

        if (!(updateUser.getBirthday() == null)) {
            oldUser.setBirthday(updateUser.getBirthday());
        }

        return oldUser;
    }

    @GetMapping
    public Collection<User> listUsers() {
        return users.values();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
