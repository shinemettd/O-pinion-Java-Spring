package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.payload.tag.TagDTO;
import kg.nurtelecom.opinion.payload.tag.TagRequest;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TagMapper {
    default Page<TagDTO> toTagDto(Page<Tag> tags) {
        return tags.map(this::toTagResponse);
    }

    TagDTO toTagResponse(Tag tag);

    Tag toTagEntity(TagRequest tagRequest);
}
