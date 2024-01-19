package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.payload.article.TagDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    List<TagDTO> toTagDto(List<Tag> tag);
}
