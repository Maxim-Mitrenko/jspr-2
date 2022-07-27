package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger size = new AtomicInteger(0);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(size.addAndGet(1));
            posts.put(post.getId(), post);
        } else {
            getById(post.getId()).orElseThrow(NotFoundException::new);
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        var result = posts.remove(id);
        if (result == null) throw new NotFoundException();
    }
}
