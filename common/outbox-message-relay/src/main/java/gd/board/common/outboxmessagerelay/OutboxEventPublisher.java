package gd.board.common.outboxmessagerelay;

import gd.board.common.event.Event;
import gd.board.common.event.EventPayload;
import gd.board.common.event.EventType;
import gd.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final Snowflake outboxSnowflake = new Snowflake();
    private final Snowflake eventSnowflake = new Snowflake();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload, Long shardKey){
        Outbox outbox = Outbox.create(
                outboxSnowflake.nextId(),
                type,
                Event.of(eventSnowflake.nextId(), type, payload).toJson(),
                shardKey % MessageRelayConstants.SHARD_COUNT
        );
        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }
}
