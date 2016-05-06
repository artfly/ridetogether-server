package io.github.artfly.ridetogether.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, V extends Serializable> extends JpaRepository<T, V> {
    Optional<T> findById(V id);
}
