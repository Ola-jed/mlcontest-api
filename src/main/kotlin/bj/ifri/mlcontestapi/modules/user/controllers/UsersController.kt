package bj.ifri.mlcontestapi.modules.user.controllers

import bj.ifri.mlcontestapi.modules.user.dto.UserDto
import bj.ifri.mlcontestapi.modules.user.dto.UserPatchDto
import bj.ifri.mlcontestapi.modules.user.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {
    @GetMapping("/current")
    fun getCurrentUser(principal: Principal): ResponseEntity<UserDto> {
        return ResponseEntity.ok(UserDto.from(userService.getCurrentUser(principal)))
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto> {
        val user = userService.getUserById(id)

        return if (user == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(UserDto.from(user))
        }
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun patch(@ModelAttribute userPatchDto: UserPatchDto, principal: Principal): ResponseEntity<UserDto> {
        return ResponseEntity.ok(UserDto.from(userService.patchUser(userPatchDto, principal)))
    }

    @DeleteMapping
    fun deleteAccount(principal: Principal): ResponseEntity<Void> {
        userService.deleteUser(principal)
        return ResponseEntity.noContent().build()
    }
}