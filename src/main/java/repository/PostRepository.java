package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository {
    private final List<Post> posts = new ArrayList<>();
    private int size = 0;

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(++size);
            posts.add(post);
        } else {
            final var found = getById(post.getId()).orElseThrow(NotFoundException::new);
            final var index = posts.indexOf(found);
            posts.remove(found);
            posts.add(index, post);
        }
        return post;
    }

    public void removeById(long id) {
        final var post = getById(id).orElseThrow(NotFoundException::new);
        posts.remove(post);
    }
}
