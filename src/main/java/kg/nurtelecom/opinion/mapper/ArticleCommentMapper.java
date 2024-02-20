package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentRequest;
import kg.nurtelecom.opinion.payload.article_comment.ArticleCommentResponse;
import kg.nurtelecom.opinion.payload.article_comment.ArticleReplyCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleCommentMapper {
    ArticleComment toEntity(ArticleCommentRequest articleCommentRequest);

    @Mapping(target = "replies",
            expression = "java(articleComment.getReplies() != null ? articleComment.getReplies().size() : 0)")
    ArticleCommentResponse toModel(ArticleComment articleComment);

    ArticleReplyCommentResponse toReplyModel(ArticleComment articleComment);
}
