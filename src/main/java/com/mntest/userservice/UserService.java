package com.mntest.userservice;

import com.mntest.dto.User;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/users")
public class UserService {

    List<User> users = new ArrayList<>();

    @Post
    public User add(@Body @Valid User user) {
        user.setUserId(users.size() + 1);
        users.add(user);
        return user;
    }

    @Put("/{id}")
    public User edit(@Nullable Integer id, @Body @Valid User updatedUser) {
        users.stream()
                .filter(it -> it.getUserId().equals(id))
                .forEach(user -> user.setUserName(updatedUser.getUserName()));
        return updatedUser;
    }

    @Get("{?limit,offset}")
    public List<User> getUsers(@Nullable Integer limit, @Nullable Integer offset) {
        return users.stream()
                .skip(offset == null ? 0 : offset)
                .limit(limit == null ? 10000 : limit)
                .collect(Collectors.toList());
    }

    @Delete("/{id}")
    public void delete(@Nullable Integer id) {
        users.removeIf(it -> it.getUserId().equals(id));
    }
}
