package gd.board.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "commen_v2")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentV2 {
    @Id
    private Long commentId;
    private String content;
    private Long articleId;
    private Long parentCommentId;
    private Long writerId;
    @Embedded
    private CommentPath commentPath;
    private Boolean deleted;
    private LocalDateTime createdAt;


}
