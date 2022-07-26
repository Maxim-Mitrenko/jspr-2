package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.hc.core5.net.WWWFormCodec;

import java.nio.charset.StandardCharsets;

public class Post {

    private long id;
    private String content;

    public Post(
            @JsonProperty("id")
            long id,
            @JsonProperty("content")
            String content) {
        this.id = id;
        this.content = WWWFormCodec.parse(content, StandardCharsets.UTF_8).get(0).getName();

    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
