package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.payload.tag.TagRequest;
import kg.nurtelecom.opinion.payload.tag.TagResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    default Page<TagResponse> toTagDto(Page<Tag> tags) {
        return tags.map(this::toTagResponse);
    }

    TagResponse toTagResponse(Tag tag);

    Tag toTagEntity(TagRequest tagRequest);
}
