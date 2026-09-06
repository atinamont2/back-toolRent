package group3.tool.rent.user.service;

import org.springframework.stereotype.Service;

import group3.tool.rent.user.dto.UserDTO;
import group3.tool.rent.user.exception.UserNotFoundException;
import group3.tool.rent.user.model.User;
import group3.tool.rent.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException(
                "Usuario no encontrado con id: " + id
            );
        }

        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getLastName(),
            user.getEmail()
        );
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            throw new UserNotFoundException(
                "Usuario no encontrado con id: " + id
            );
        }

        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException(
                "Usuario no encontrado con id: " + id
            );
        }

        userRepository.deleteById(id);
    }
}