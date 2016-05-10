package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(Long routeId, Integer count, Long since);
    CommentDto addComment(CurrentUser currentUser, Long routeId, CommentDto commentDto);
    void deleteComment(CurrentUser currentUser, Long commentId);
}
