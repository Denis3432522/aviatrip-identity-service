package org.aviatrip.identityservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.identityservice.dto.request.auth.UpdateUserEmailRequest;
import org.aviatrip.identityservice.dto.request.auth.UpdateUserNameRequest;
import org.aviatrip.identityservice.dto.request.auth.UpdateUserSurnameRequest;
import org.aviatrip.identityservice.dto.response.error.UserInfo;
import org.aviatrip.identityservice.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public UserInfo user(@RequestHeader("subject") UUID userId) {
        return profileService.retrieveUserInfo(userId);
    }

    @PatchMapping("/name")
    public void changeName(@RequestBody @Valid UpdateUserNameRequest model, @RequestHeader("subject") UUID userId) {
        profileService.updateName(model.name(), userId);
    }

    @PatchMapping("/surname")
    public void changeSurname(@RequestBody @Valid UpdateUserSurnameRequest model, @RequestHeader("subject") UUID userId) {
        profileService.updateSurname(model.surname(), userId);
    }

    @PatchMapping("/email")
    public void changeEmail(@RequestBody @Valid UpdateUserEmailRequest model, @RequestHeader("subject") UUID userId) {
        profileService.updateEmail(model.email(), userId);
    }
}
