package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.enums.TagStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void saveTag() {
        Tag tag = new Tag("Test Tag", TagStatus.ON_MODERATION);

        tagRepository.save(tag);

        Assertions.assertNotNull(tag.getId());
    }

    @Test
    public void findTagById() {

        Tag tag = new Tag("Test Tag", TagStatus.APPROVED);
        tagRepository.save(tag);

        Optional<Tag> foundTag = tagRepository.findById(tag.getId());

        Assertions.assertTrue(foundTag.isPresent());
        Assertions.assertEquals("Test Tag", foundTag.get().getTagName());
    }

    @Test
    public void testDeleteTag() {
        Tag tag = new Tag("Test Tag", TagStatus.ON_MODERATION);
        tagRepository.save(tag);
        tagRepository.delete(tag);

        Optional<Tag> deletedTag = tagRepository.findById(tag.getId());
        Assertions.assertFalse(deletedTag.isPresent());
    }
}
