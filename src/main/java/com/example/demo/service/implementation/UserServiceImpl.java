package com.example.demo.service.implementation;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.form.UpdateForm;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.dtomapper.UserDTOMapper.fromUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRepository;

    @Override
    public UserDTO createUser(User user) {
        return mapUserToDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapUserToDTO(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);
    }

    @Override
    public UserDTO verifyCode(String email, String code) {
        return mapUserToDTO(userRepository.verifyCode(email, code));
    }

    @Override
    public void resetPassword(String email) {
        userRepository.resetPassword(email);
    }

    @Override
    public UserDTO verifyPasswordKey(String key) {
        return mapUserToDTO(userRepository.verifyPasswordKey(key));
    }

    @Override
    public void renewPasswordKey(String key, String password, String confirmPassword) {
        userRepository.renewPassword(key, password, confirmPassword);
    }

    @Override
    public UserDTO verifyAccountKey(String key) {
        return mapUserToDTO(userRepository.verifyAccountKey(key));
    }

    @Override
    public UserDTO updateUserDetails(UpdateForm user) {
        return mapUserToDTO(userRepository.updateUserDetails(user));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return mapUserToDTO(userRepository.get(userId));
    }

    @Override
    public void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword) {
        userRepository.updatePassword(id, currentPassword, newPassword, confirmNewPassword);
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        roleRepository.updateUserRole(userId, roleName);
    }

    @Override
    public void updateAccountSettings(Long userId, Boolean enabled, Boolean notLocked) {
        userRepository.updateAccountSettings(userId, enabled, notLocked);
    }

    @Override
    public UserDTO toggleMfa(String email) {
        return mapUserToDTO(userRepository.toggleMfa(email));
    }

    @Override
    public void updateImage(UserDTO user, MultipartFile image) {
        userRepository.updateImage(user, image);
    }

    private UserDTO mapUserToDTO(User user) {
        return fromUser(user, roleRepository.getRoleByUserId(user.getId()));
    }
}
