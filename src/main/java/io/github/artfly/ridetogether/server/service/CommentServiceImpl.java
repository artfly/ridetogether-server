package io.github.artfly.ridetogether.server.service;

import io.github.artfly.ridetogether.server.repository.CommentRepository;
import io.github.artfly.ridetogether.server.repository.CoordinateRepository;
import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.RouteRepository;
import io.github.artfly.ridetogether.server.repository.entities.Comment;
import io.github.artfly.ridetogether.server.repository.entities.Route;
import io.github.artfly.ridetogether.server.service.exceptions.AuthorizeException;
import io.github.artfly.ridetogether.server.service.security.CurrentUser;
import io.github.artfly.ridetogether.server.utils.Utils;
import io.github.artfly.ridetogether.server.web.dto.comment.CommentDto;
import io.github.artfly.ridetogether.server.web.dto.comment.ContentDto;
import io.github.artfly.ridetogether.server.web.dto.route.GeometryDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private static final int MAX_COUNT = 200;
    private final CommentRepository commentRepository;
    private final RouteRepository routeRepository;
    private final ImageRepository imageRepository;
    private final CoordinateRepository coordinateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, RouteRepository routeRepository,
                              ImageRepository imageRepository, CoordinateRepository coordinateRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.imageRepository = imageRepository;
        this.coordinateRepository = coordinateRepository;
        this.modelMapper = modelMapper;
        modelMapper.addMappings(new PropertyMap<CommentDto, Comment>() {
            @Override
            protected void configure() {
                map().setText(source.getContent().getText());
                map().setRating(0);
                map().setCoordinates(source.getContent().getGeometry().getCoordinates());
                skip().setAddedAt(null);
            }
        });
        modelMapper.addMappings(new PropertyMap<Comment, CommentDto>() {
          @Override
          protected void configure() {
              map().setId(source.getId());
              map().setAddedAt(source.getAddedAt());
              map().setContent(new ContentDto());
              map().getContent().setText(source.getText());
              map().getContent().setGeometry(new GeometryDto());
              map().getContent().getGeometry().setCoordinates(source.getCoordinates());
              map().getContent().getGeometry().setType("LineString");
              map().getContent().setPics(source.getPicsPaths());
          }
        });
    }

    @Override
    public List<CommentDto> getComments(Long routeId, Integer count, Long since) {
        Pageable pageable;
        List<Comment> comments;
        if (count > MAX_COUNT) {
            count = MAX_COUNT;
        }
        pageable = new PageRequest(0, count);

        if(since == null) {
            comments = commentRepository.findByRoute_IdEquals(routeId, pageable);
        } else {
            comments = commentRepository.findByRoute_IdEqualsAndAddedAtLessThan(routeId, since, pageable);
        }
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(CurrentUser currentUser, Long routeId, CommentDto commentDto) {
        Route route = Utils.validate(routeId, routeRepository);
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setRoute(route);
        comment.setPics(commentDto.getContent().getPics()
                .stream()
                .map(imagePath -> Utils.validate(imagePath, imageRepository))
                .collect(Collectors.toList()));
        comment.setCreator(currentUser.getUser());
        coordinateRepository.save(comment.getDbCoordinates());
        commentRepository.save(comment);
        System.out.println(comment.getText());
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(CurrentUser currentUser, Long commentId) {
        Comment comment = Utils.validate(commentId, commentRepository);
        if (!currentUser.getUsername().equals(comment.getCreator().getUsername())) {
            throw new AuthorizeException(currentUser.getUsername());
        }
        commentRepository.delete(comment);
    }
}
