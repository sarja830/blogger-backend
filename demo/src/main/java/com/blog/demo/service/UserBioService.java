package com.blog.demo.service;

import com.blog.demo.dto.UserBioDTO;
import com.blog.demo.model.user.User;
import com.blog.demo.model.user.UserBio;

import com.blog.demo.repository.UserBioRepository;
import com.blog.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class  UserBioService {

   private final UserBioRepository userBioRepository;
   private final UserRepository userRepository;
   private final AuthService authService;

    public void updateUserBio(UserBioDTO userBioDTO, Jwt jwt) {

        User user = authService.getUserFromJwt(jwt);
        UserBio userBio =  userBioRepository.findByUserId(user.getId()).orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("User doesn't exist")));
        if(userBioDTO.getProfileImage()!=null)
            userBio.getUser().setProfileImage(userBioDTO.getProfileImage());
        if(userBioDTO.getFacebook() != null)
            userBio.setFacebook(userBioDTO.getFacebook());
        if(userBioDTO.getTwitter() != null)
            userBio.setTwitter(userBioDTO.getTwitter());
        if(userBioDTO.getLinkedin() != null)
            userBio.setLinkedin(userBioDTO.getLinkedin());
        if(userBioDTO.getGithub() != null)
            userBio.setGithub(userBioDTO.getGithub());
        if(userBioDTO.getInstagram() != null)
            userBio.setInstagram(userBioDTO.getInstagram());
        if(userBioDTO.getYoutube() != null)
            userBio.setYoutube(userBioDTO.getYoutube());
        if(userBioDTO.getBio() != null)
            userBio.setBio(userBioDTO.getBio());
        if(userBioDTO.getWebsite() != null)
            userBio.setWebsite(userBioDTO.getWebsite());

        userBioRepository.save(userBio);

    }

    public String updateUserProfileImage(String profileImage,Jwt jwt,String username, String name)
    {
        User user = authService.getUserFromJwt(jwt);
        boolean flag = false;
        if(username!=null && !username.equals(user.getUsername()) ) {
            user.setUsername(username);
            flag = true;
        }
        if(name!=null && !name.equals(user.getName())) {
            user.setName(name);
            flag = true;
        }
        if(profileImage!=null && !profileImage.equals(user.getProfileImage())) {
            user.setProfileImage(profileImage);
            flag = true;
        }
        if(flag) {
            userRepository.save(user);
            return "Profile Image updated successfully";
        }
        return "No changes made";

    }

    public UserBioDTO getUserBio(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("User doesn't exist")));
        UserBio userBio =  userBioRepository.findByUserId(user.getId()).orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("User doesn't exist")));
        UserBioDTO userBioDTO = UserBioDTO.builder()
                .bio(userBio.getBio())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .email(user.getEmail())
                .created(user.getCreated())
                .name(user.getName())
                .facebook(userBio.getFacebook())
                .github(userBio.getGithub())
                .instagram(userBio.getInstagram())
                .twitter(userBio.getTwitter())
                .linkedin(userBio.getLinkedin())
                .website(userBio.getWebsite())
                .youtube(userBio.getYoutube())
                .build();

        return userBioDTO ;
    }

}
