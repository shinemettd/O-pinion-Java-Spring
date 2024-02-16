package kg.nurtelecom.opinion.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kg.nurtelecom.opinion.entity.Tag;
import kg.nurtelecom.opinion.payload.tag.TagResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-16T20:00:07+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public List<TagResponse> toTagDto(List<Tag> tag) {
        if ( tag == null ) {
            return null;
        }

        List<TagResponse> list = new ArrayList<TagResponse>( tag.size() );
        for ( Tag tag1 : tag ) {
            list.add( tagToTagResponse( tag1 ) );
        }

        return list;
    }

    protected TagResponse tagToTagResponse(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        Long id = null;

        id = tag.getId();

        String name = null;

        TagResponse tagResponse = new TagResponse( id, name );

        return tagResponse;
    }
}
