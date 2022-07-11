package config;

import controller.PostController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.PostRepository;
import service.PostService;

@Configuration
public class Config {

    @Bean
    public PostController postController(PostService postService) {
        return new PostController(postService);
    }

    @Bean
    public PostService postService(PostRepository postRepository) {
        return new PostService(postRepository);
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}
