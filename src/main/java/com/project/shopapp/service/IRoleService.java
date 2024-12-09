package com.project.shopapp.service;

import java.util.List;

import com.project.shopapp.dto.request.RoleRequest;
import com.project.shopapp.entity.Role;

public interface IRoleService {
    Role createRole(RoleRequest request);

    List<Role> getRoles();

    void deleteRole(Long id);

    Role updateRole(Long id, RoleRequest request);
}
