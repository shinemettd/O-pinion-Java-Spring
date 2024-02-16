package kg.nurtelecom.opinion.mapper;

import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.Complaint;
import kg.nurtelecom.opinion.enums.Reason;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class ComplaintMapperImpl implements ComplaintMapper {

    @Override
    public Complaint toEntity(ComplaintDTO complaintDTO) {
        if ( complaintDTO == null ) {
            return null;
        }

        Complaint complaint = new Complaint();

        complaint.setReason( complaintDTO.reason() );
        complaint.setText( complaintDTO.text() );

        return complaint;
    }

    @Override
    public ComplaintDTO toModel(Complaint complaint) {
        if ( complaint == null ) {
            return null;
        }

        Reason reason = null;
        String text = null;

        reason = complaint.getReason();
        text = complaint.getText();

        ComplaintDTO complaintDTO = new ComplaintDTO( reason, text );

        return complaintDTO;
    }
}
