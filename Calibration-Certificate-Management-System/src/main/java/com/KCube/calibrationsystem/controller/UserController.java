package com.KCube.calibrationsystem.controller;

import com.KCube.calibrationsystem.Dtos.UserDTO;
import com.KCube.calibrationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public List<UserDTO> searchByUsername(@RequestParam String username) {
        return service.searchByUsername(username);
    }
}
