package com.project.shopapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.DuplicateEntryException;
import com.project.shopapp.model.dto.request.RoleRequest;
import com.project.shopapp.model.entity.Role;
import com.project.shopapp.repository.RoleRepository;
import com.project.shopapp.service.RoleService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    MessageUtils messageUtils;

    @Override
    public Role createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateEntryException(messageUtils.getMessage(MessageKeys.ROLE_ALREADY_EXISTS));
        }
        Role role = Role.builder().name(request.getName()).build();
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Long id, RoleRequest request) throws RuntimeException {
        Role existingRole = roleRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(messageUtils.getMessage(MessageKeys.ROLE_NOT_FOUND)));
        existingRole.setName(request.getName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new DuplicateEntryException(messageUtils.getMessage(MessageKeys.ROLE_NOT_FOUND));
        }
        roleRepository.deleteById(id);
    }
}
