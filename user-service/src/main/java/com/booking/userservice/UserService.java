package com.booking.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User create(User user) {
        user.setId(null);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        User existing = getById(id);

        String newEmail = user.getEmail();
        if (newEmail != null && !newEmail.equalsIgnoreCase(existing.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
            }
        }

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());

        return userRepository.save(existing);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}