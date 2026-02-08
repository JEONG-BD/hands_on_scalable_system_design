package gd.board.article.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class ArticleCreateRequest {
    private String content;
    private String title;
    private Long writerId;
    private Long boardId;
}
