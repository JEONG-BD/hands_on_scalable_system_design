package gd.board.article.service.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleUpdateRequest {
    private String content;
    private String title;
    private Long writerId;
    private Long boardId;
}
