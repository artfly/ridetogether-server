package io.github.artfly.ridetogether.server.web;

import io.github.artfly.ridetogether.server.service.CommentService;
import io.github.artfly.ridetogether.server.service.RouteService;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.web.dto.comment.CommentDto;
import io.github.artfly.ridetogether.server.web.dto.route.RouteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {
    private static final String DEFAULT_COUNT = "20";
    private static final String DEFAULT_OFFSET = "0";
    private final RouteService routeService;
    private final CommentService commentService;

    @Autowired
    public RouteController(RouteService routeService, CommentService commentService) {
        this.routeService = routeService;
        this.commentService = commentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RouteDto>> getRoutes(@RequestParam(value = "count", required = false, defaultValue = DEFAULT_COUNT) Integer count,
                                                    @RequestParam(value = "place") String place,
                                                    @RequestParam(value = "type", required = false) String routeType,
                                                    @RequestParam(value = "since", required = false) Long since) {
        return new ResponseEntity<>(routeService.getRoutes(place, count, since, routeType), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RouteDto> addRoute(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody RouteDto routeDto) {
        return new ResponseEntity<>(routeService.addRoute(currentUser, routeDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{routeId}", method = RequestMethod.GET)
    public ResponseEntity<RouteDto> getRoute(@PathVariable Long routeId) {
        return new ResponseEntity<>(routeService.getRoute(routeId), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{routeId}", method = RequestMethod.DELETE)
    ResponseEntity<HttpStatus> deleteRoute(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long routeId) {
        routeService.deleteRoute(currentUser, routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{routeId}/comments", method = RequestMethod.POST)
    ResponseEntity<CommentDto> addComment(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long routeId,
                                          @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addComment(currentUser, routeId, commentDto), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "{routeId}/comments/{commentId}", method = RequestMethod.DELETE)
    ResponseEntity<HttpStatus> deleteComment(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long routeId,
                                             @PathVariable Long commentId) {
        commentService.deleteComment(currentUser, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{routeId}/comments", method = RequestMethod.GET)
    ResponseEntity<List<CommentDto>> getComments(@PathVariable Long routeId,
                                                 @RequestParam(value = "count", required = false, defaultValue = DEFAULT_COUNT) Integer count,
                                                 @RequestParam(value = "since", required = false) Long since) {
        return new ResponseEntity<>(commentService.getComments(routeId, count, since), HttpStatus.OK);
    }
}
