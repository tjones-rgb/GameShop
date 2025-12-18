package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController
{
    private ProfileDao profileDao;
    private UserDao userDao;

    public ProfileController(ProfileDao profileDao, UserDao userDao)
    {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }


    @GetMapping
    public Profile getProfile(Principal principal)
    {
        User user = userDao.getByUserName(principal.getName());
        return profileDao.getByUserId(user.getId());
    }


    @PutMapping
    public Profile updateProfile(Principal principal, @RequestBody Profile profile)
    {
        User user = userDao.getByUserName(principal.getName());
        profile.setUserId(user.getId());

        profileDao.update(profile);
        return profileDao.getByUserId(user.getId());
    }
}
