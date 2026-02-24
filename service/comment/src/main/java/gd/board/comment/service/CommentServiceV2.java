package gd.board.comment.service;

import gd.board.comment.entity.CommentPath;
import gd.board.comment.entity.CommentV2;
import gd.board.comment.repository.CommentRepositoryV2;
import gd.board.comment.service.request.CommentCreateRequestV2;
import gd.board.comment.service.response.CommentResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

import static java.util.function.Predicate.*;

@Service
@RequiredArgsConstructor
public class CommentServiceV2 {

    private final Snowflake snowflake = new Snowflake();
    private final CommentRepositoryV2 commentRepositoryV2;

    @Transactional
    public CommentResponse create(CommentCreateRequestV2 request){
        CommentV2 parent = findParent(request);

        CommentPath parentCommentPath = parent == null ? CommentPath.create("") : parent.getCommentPath();

        CommentV2 comment = commentRepositoryV2.save(
                CommentV2.create(
                        snowflake.nextId(),
                        request.getContent(),
                        request.getArticleId(),
                        request.getWriterId(),
                        parentCommentPath.createChildCommentPath(
                                commentRepositoryV2.findDescendantsTopPath(request.getArticleId(), parentCommentPath.getPath())
                                        .orElse(null))
                )
        );
        return CommentResponse.from(comment);

    }

    private CommentV2 findParent(CommentCreateRequestV2 request) {
        String parentPath = request.getParentPath();
        if (parentPath == null) {
            return null;
        }

        return commentRepositoryV2.findByPath(parentPath).filter(CommentV2::getDeleted).orElseThrow();

    }

    public CommentResponse read(Long commentId){
        return CommentResponse.from(commentRepositoryV2.findById(commentId)
                .orElseThrow());
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepositoryV2.findById(commentId)
                .filter(not(CommentV2::getDeleted))
                .ifPresent(comment -> {
                            if (hasChildren(comment)) {
                                comment.delete();
                            } else {
                                delete(comment);
                            }

                        }
                );
    }


    private boolean hasChildren(CommentV2 comment) {
        return commentRepositoryV2.findDescendantsTopPath(
                comment.getArticleId(),
                comment.getCommentPath().getPath()
        ).isPresent();
    }

    private void delete(CommentV2 comment) {
        commentRepositoryV2.delete(comment);
        if (!comment.isRoot()) {
            commentRepositoryV2.findByPath(comment.getCommentPath().getParentPath())
                    .filter(CommentV2::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

}



