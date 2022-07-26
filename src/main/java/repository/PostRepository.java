package repository;

import exception.NotFoundException;
import model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {
    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicInteger size = new AtomicInteger();

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
            post.setId(size.addAndGet(1));
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
