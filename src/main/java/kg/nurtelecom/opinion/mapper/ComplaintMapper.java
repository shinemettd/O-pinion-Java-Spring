package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Complaint;
import kg.nurtelecom.opinion.payload.complaint.ComplaintDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    Complaint toEntity(ComplaintDTO complaintDTO);
    ComplaintDTO toModel(Complaint complaint);
}
