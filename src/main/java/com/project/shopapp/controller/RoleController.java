package com.project.shopapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dto.request.RoleRequest;
import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.entity.Role;
import com.project.shopapp.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<Role>builder()
                .code(HttpStatus.CREATED.value())
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getRoles() {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .result(roleService.getRoles())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Role> updateRole(@PathVariable Long id, @RequestBody @Valid RoleRequest request) {
        return ApiResponse.<Role>builder()
                .code(HttpStatus.OK.value())
                .result(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Role deleted successfully")
                .build();
    }
}
