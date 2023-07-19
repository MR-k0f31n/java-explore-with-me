package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.input.NewUserDto;
import ru.practicum.dto.user.UserDto;

import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface UserService {
    UserDto created(NewUserDto newUser);

    List<UserDto> findUserById(List<Long> ids, Pageable page);

    void delete(Long userId);
}
