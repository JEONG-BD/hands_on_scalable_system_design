package gd.board.common.event;


import gd.board.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.GD_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.GD_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.GD_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.GD_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.GD_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.GD_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.GD_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.GD_BOARD_VIEW)
    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type){

        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType from] type={}" , type, e);
            return null;
        }
    }


    public static class Topic {
        public static final String GD_BOARD_ARTICLE = "gd-board-article";
        public static final String GD_BOARD_COMMENT = "gd-board-comment";
        public static final String GD_BOARD_LIKE = "gd-board-like";
        public static final String GD_BOARD_VIEW = "gd-board-view";
    }


}
