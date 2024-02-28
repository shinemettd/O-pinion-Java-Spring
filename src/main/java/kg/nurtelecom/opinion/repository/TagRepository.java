package kg.nurtelecom.opinion.repository;


import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.enums.TagStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByStatus(@Param("status") TagStatus status, Pageable pageable);

    Page<Tag> findByNameContaining(@Param("name") String name, Pageable pageable);

    Optional<Tag> findByName(@Param("name") String name);


}
