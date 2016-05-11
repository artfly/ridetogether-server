package io.github.artfly.ridetogether.server.repository;


import io.github.artfly.ridetogether.server.repository.entities.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findByRoute_IdEquals(Long routeId, Pageable pageable);

    List<Comment> findByRoute_IdEqualsAndAddedAtLessThan(Long routeId, Long addedAt, Pageable pageable);
}
