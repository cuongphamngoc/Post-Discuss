package com.cuongpn.service.Impl;

import com.cuongpn.entity.Role;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.repository.RoleRepository;
import com.cuongpn.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

}
