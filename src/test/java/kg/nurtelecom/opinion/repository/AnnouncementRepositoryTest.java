//package kg.nurtelecom.opinion.repository;
//
//import kg.nurtelecom.opinion.entity.Announcement;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class AnnouncementRepositoryTest {
//
//    @Autowired
//    AnnouncementRepository repository;
//
//    @Test
//    void findAllShouldReturnNotEmptyAnnouncements() {
//        Iterable<Announcement> announcements = repository.findAll();
//
//        assertThat(announcements).isNotEmpty();
//    }
//
//    @Test
//    void saveShouldReturnValidAnnouncement() {
//        Announcement announcement = new Announcement();
//        announcement.setId(1L);
//        announcement.setTitle("Test title");
//        announcement.setContent("Test content");
//        announcement.setCoverImage("test_path.png");
//
//        Announcement savedAnnouncement = repository.save(announcement);
//
//        assertThat(savedAnnouncement).hasFieldOrPropertyWithValue("id", 1L);
//        assertThat(savedAnnouncement).hasFieldOrPropertyWithValue("title", "Test title");
//        assertThat(savedAnnouncement).hasFieldOrPropertyWithValue("content", "Test content");
//        assertThat(savedAnnouncement).hasFieldOrPropertyWithValue("coverImage", "test_path.png");
//    }
//
//    @Test
//    void findByIdShouldReturnFirstAnnouncement() {
//        Long id = 1L;
//        Announcement foundAnnouncement = (repository.findById(id)).get();
//
//        assertThat(foundAnnouncement).isNotNull();
//        assertThat(foundAnnouncement).hasFieldOrPropertyWithValue("id", id);
//    }
//}