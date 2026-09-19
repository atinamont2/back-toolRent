package group3.tool.rent.user.service;

import group3.tool.rent.user.dto.UserRequestDTO;
import group3.tool.rent.user.dto.UserResponseDTO;
import group3.tool.rent.user.exception.UserNotFoundException;
import group3.tool.rent.user.model.User;
import group3.tool.rent.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> findAll() {
        List<UserResponseDTO> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            users.add(toDTO(user));
        }

        return users;
    }

    public UserResponseDTO findById(Long id) {
        return toDTO(findUserOrThrow(id));
    }

    public UserResponseDTO saveUser(UserRequestDTO request) {
        User user = new User();
        copyRequestToUser(request, user);

        return toDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user = findUserOrThrow(id);
        copyRequestToUser(request, user);

        return toDTO(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "Usuario no encontrado con id: " + id
                ));
    }

    private void copyRequestToUser(UserRequestDTO request, User user) {
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}