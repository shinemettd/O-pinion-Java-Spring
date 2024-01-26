package kg.nurtelecom.opinion.mapper;

import kg.nurtelecom.opinion.entity.ArticleComment;
import kg.nurtelecom.opinion.payload.comment.CommentRequest;
import kg.nurtelecom.opinion.payload.comment.CommentResponse;
import kg.nurtelecom.opinion.payload.comment.ReplyCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleCommentMapper {
    ArticleComment toEntity(CommentRequest commentRequest);

    @Mapping(target = "replies",
            expression = "java(articleComment.getReplies() != null ? articleComment.getReplies().size() : 0)")
    CommentResponse toModel(ArticleComment articleComment);

    ReplyCommentResponse toReplyModel(ArticleComment articleComment);
}
