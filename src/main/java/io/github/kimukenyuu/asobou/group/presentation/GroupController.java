package io.github.kimukenyuu.asobou.group.presentation;

import io.github.kimukenyuu.asobou.group.application.CreateGroupService;
import io.github.kimukenyuu.asobou.group.domain.Group;
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

@Tag(name = "Group", description = "Group management API")
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final CreateGroupService createGroupService;

    public GroupController(CreateGroupService createGroupService) {
        this.createGroupService = createGroupService;
    }

    @Operation(
            summary = "Create group",
            description = "Creates a group and registers the creator as an OWNER member."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Group created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Creator user not found"
            )
    })
    @PostMapping
    public ResponseEntity<CreateGroupResponse> createGroup(
            @Valid @RequestBody CreateGroupRequest request
    ) {
        Group group = createGroupService.create(
                request.creatorId(), request.name(), request.description()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateGroupResponse.from(group, request.creatorId()));
    }
}
