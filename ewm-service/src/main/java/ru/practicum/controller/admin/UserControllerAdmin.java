package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.input.NewUserDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author MR.k0F31n
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/users")
public class UserControllerAdmin {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsersInfoByIds(@RequestParam(value = "ids", required = false) List<Long> ids,
                                           @RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Endpoint request: GET admin/users");
        log.debug("Param: array ids = '{}', from = '{}', size = '{}'", ids, from, size);
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return userService.findUserById(ids, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto created(@Valid @RequestBody NewUserDto input) {
        log.trace("Endpoint request: POST admin/users");
        log.debug("Param: input body '{}'", input);
        return userService.created(input);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "userId") @Min(1) Long userId) {
        log.trace("Endpoint request: DELETE admin/users");
        log.debug("Param: Path variable '{}'", userId);
        userService.delete(userId);
    }
}
