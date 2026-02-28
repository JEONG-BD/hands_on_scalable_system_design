package gd.board.articleread.service.event.handler;

import gd.board.common.event.Event;
import gd.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
