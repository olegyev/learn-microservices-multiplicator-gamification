package learn.microservices.gamification.game.entity;

import learn.microservices.gamification.game.enumeration.BadgeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("badge_cards")
public class BadgeCard {

    @Id
    private String id;

    private String userId;

    @EqualsAndHashCode.Exclude
    private long timestamp;

    private BadgeType type;

    public BadgeCard(final String userId, final BadgeType type) {
        this(null, userId, System.currentTimeMillis(), type);
    }

}