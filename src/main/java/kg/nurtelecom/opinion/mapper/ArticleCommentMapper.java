package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleNestedCommentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleCommentMapper {
    ArticleComment toEntity(ArticleCommentRequest articleCommentRequest);
    ArticleCommentResponse toModel(ArticleComment articleComment);
    ArticleNestedCommentResponse toNestedModel(ArticleComment articleComment);
}
