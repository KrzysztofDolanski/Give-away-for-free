package com.example.gaff;

import com.example.gaff.api_user.*;
import com.example.gaff.api_user.map.GoogleMapsClientProperties;
import com.example.gaff.article.Article;
import com.example.gaff.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
//        (exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@EnableConfigurationProperties(GoogleMapsClientProperties.class)

@RequiredArgsConstructor
public class GaffApplication implements CommandLineRunner {

    private final ArticleRepository articleRepository;
    private final ApiUserRepository apiUserRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(GaffApplication.class, args);
    }
        String root = bCryptPasswordEncoder().encode("root");

    @Override
    public void run(String... args) throws Exception {
        apiUserRepository.deleteAll();
        articleRepository.deleteAll();
        confirmationTokenRepository.deleteAll();


        ApiUser save = apiUserRepository.save(ApiUser.builder()
                .username("root")
                .password(root)
                .city("Gdansk")
                .street("Wieckowskiego")
                .streetNo("7")
                .enabled(true)
                .userRole(ApiUserRole.USER)
                .build());
        ConfirmationToken confirmationToken = new ConfirmationToken(save);
        confirmationTokenRepository.save(confirmationToken);

        apiUserRepository.save(ApiUser.builder()
                .username("Krzysztof")
                .password(root)
                .userRole(ApiUserRole.USER)
                .city("Gdansk")
                .street("Pomorska")
                .streetNo("68")
                .enabled(true)
                .build());

        articleRepository.save(Article.builder()
                .title("Free article 1")
                .userId(2l)
                .description("description")
                .img(new byte[123])
                .isAvailable(true)
                .build());

        articleRepository.save(Article.builder()
                .title("Free article 2")
                .userId(2l)
                .description("description")
                .img(new byte[123])
                .isAvailable(true)
                .build());

        articleRepository.save(Article.builder()
                .title("Free article 3")
                .userId(2l)
                .description("description")
                .img(new byte[123])
                .isAvailable(true)
                .build());

        articleRepository.save(Article.builder()
                .title("Free article 4")
                .userId(2l)
                .description("description")
                .img(new byte[123])
                .isAvailable(true)
                .build());

        articleRepository.save(Article.builder()
                .title("Free article 5")
                .userId(2l)
                .description("description")
                .img(new byte[123])
                .isAvailable(true)
                .build());
    }
}
