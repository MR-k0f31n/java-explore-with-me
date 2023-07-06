package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.input.NewUserDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.exception.EmailConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.UserMapper.toDto;
import static ru.practicum.mapper.UserMapper.toModel;

/**
 * @author MR.k0F31n
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDto created(NewUserDto newUser) {
        log.info("Task created new user {}", newUser);
        final User user = toModel(newUser);
        log.debug("try save new user {}", user);
        validationEmail(newUser.getEmail());
        final User result = userRepository.save(user);
        log.debug("User created successfully, user id {}", result.getId());
        return toDto(result);
    }

    @Override
    public List<UserDto> findUserById(Long[] ids, Pageable page) {
        log.info("Task get collection users");
        List<User> result = (ids != null) ?
                userRepository.findByIdIn(ids, page) :
                userRepository.findAll(page).getContent();
        log.debug("Number of users found '{}'", result.size());
        return result.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        log.info("Task deleted user by id {}", userId);
        if (!userRepository.existsById(userId)) throw new NotFoundException("User with id '" + userId + "' not found");
        userRepository.deleteById(userId);
        log.debug("User id {} deleted is {}", userId, !userRepository.existsById(userId));
    }

    private void validationEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailConflictException("Email '" + email + "' is exist");
        }
    }
}
