package io.github.kimukenyuu.asobou.user.api;

import io.github.kimukenyuu.asobou.user.application.CreateUserService;
import io.github.kimukenyuu.asobou.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "User",
        description = "User management API"
)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserService createUserService;

    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @Operation(
            summary = "Create user",
            description = "Creates a user with the local authentication provider."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email or username already exists"
            )
    })
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        User user = createUserService.create(
                request.email(),
                request.username(),
                request.displayName()
        );

        CreateUserResponse response =
                CreateUserResponse.from(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}