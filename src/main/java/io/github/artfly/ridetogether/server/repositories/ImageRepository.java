package io.github.artfly.ridetogether.server.repositories;

import io.github.artfly.ridetogether.server.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ImageRepository extends BaseRepository<Image, String> {
}
