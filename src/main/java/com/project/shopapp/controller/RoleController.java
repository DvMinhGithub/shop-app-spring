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

import com.project.shopapp.model.dto.request.RoleRequest;
import com.project.shopapp.model.dto.response.ApiResponse;
import com.project.shopapp.model.entity.Role;
import com.project.shopapp.service.impl.RoleServiceImpl;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;
    private final MessageUtils messageUtils;

    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<Role>builder()
                .code(HttpStatus.CREATED.value())
                .message(messageUtils.getMessage(MessageKeys.ROLE_CREATE_SUCCESS))
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<?> getRoles() {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageUtils.getMessage(MessageKeys.ROLE_LIST_SUCCESS))
                .result(roleService.getRoles())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Role> updateRole(@PathVariable Long id, @RequestBody @Valid RoleRequest request) {
        return ApiResponse.<Role>builder()
                .code(HttpStatus.OK.value())
                .message(MessageKeys.ROLE_UPDATE_SUCCESS)
                .result(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(MessageKeys.ROLE_DELETE_SUCCESS)
                .build();
    }
}
