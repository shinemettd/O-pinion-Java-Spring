package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Complain;
import kg.nurtelecom.opinion.payload.complain.ComplainDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplainMapper {

    Complain toComplain(ComplainDTO complainDTO);
    ComplainDTO toComplainDTO(Complain complain);
}
