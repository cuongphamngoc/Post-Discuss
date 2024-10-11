package com.cuongpn.config;

import com.cuongpn.entity.Role;
import com.cuongpn.entity.Tag;
import com.cuongpn.entity.User;
import com.cuongpn.enums.TagType;
import com.cuongpn.repository.RoleRepository;
import com.cuongpn.repository.TagRepository;
import com.cuongpn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppInitializer {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TagRepository tagRepository;
    @Value("${user.default.avatar.url}")
    private String avatarUrl;

    @Value("${tag.user.default}")
    private String userTag;
    @Value("${tag.admin.default}")
    private String adminTag;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(()->{
                Role role = new Role("ROLE_ADMIN");
                return roleRepository.save(role);
            });
            if(adminRole.getUsers().isEmpty()){
                System.out.println("Initialize admin account");

                User admin = User.builder()
                        .name("Admin")
                        .email("admin@gmail.com")
                        .avatarUrl(avatarUrl)
                        .roles(Set.of(adminRole))
                        .password(passwordEncoder.encode("asdasd"))
                        .isVerification(true)
                        .build();


                User res = userRepository.save(admin);
                adminRole.getUsers().add(res);
                roleRepository.save(adminRole);
                List<Tag> tags = tagRepository.findAll();
                if(tags.isEmpty()){
                    String[] userTagNameArr = userTag.split(",");
                    List<Tag> userTaglist = Arrays.stream(userTagNameArr).map(s->{
                        return new Tag(s.trim(), TagType.USER);
                    }).toList();
                    tags.addAll(userTaglist);
                    String [] adminTagNameArr = adminTag.split(",");
                    List<Tag> adminTagList = Arrays.stream(adminTagNameArr).map(s->{
                        return new Tag(s.trim(), TagType.ADMIN);
                    }).toList();
                    tags.addAll(adminTagList);
                }
                tagRepository.saveAll(tags);

            }


        };
    }



}
