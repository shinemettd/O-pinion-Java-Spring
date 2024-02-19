package kg.nurtelecom.opinion.repository;

import kg.nurtelecom.opinion.entity.Complaint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ComplaintRepositoryTest {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(complaintRepository).isNotNull();
    }

    @Test
    public void saveComplaint() {

        Complaint complaint = new Complaint();

        Complaint savedComplaint = complaintRepository.save(complaint);

        assertThat(savedComplaint.getId()).isNotNull();
    }


    @Test
    public void deleteComplaint() {
        Complaint complaint = new Complaint();
        complaint.setText("Test Complaint");

        Complaint savedComplaint = complaintRepository.save(complaint);

        complaintRepository.delete(savedComplaint);

        assertThat(complaintRepository.findById(savedComplaint.getId())).isEmpty();
    }

    @Test
    public void findAllComplaints() {

        Complaint complaint1 = new Complaint();
        complaint1.setText("Complaint 1");

        complaintRepository.save(complaint1);

        Iterable<Complaint> complaints = complaintRepository.findAll();

        assertThat(complaints).isNotNull();

    }

    @AfterEach
    public void tearDown() {
        complaintRepository.deleteAll();
    }
}
