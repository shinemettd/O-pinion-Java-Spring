package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleCommentMapper {
    ArticleComment toEntity(CommentRequest commentRequest);
    CommentResponse toModel(ArticleComment articleComment);
}