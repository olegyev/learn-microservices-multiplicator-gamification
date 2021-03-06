package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.entity.BadgeCard;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import learn.microservices.gamification.game.repository.BadgeRepository;
import learn.microservices.gamification.game.repository.ScoreRepository;
import learn.microservices.gamification.game.repository.ScoreRepository.UserTotalScore;
import learn.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // both scorecards and badges are stored or none of them if something goes wrong
@Slf4j
public class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;

    private final BadgeRepository badgeRepository;

    // Spring injects all the @Component beans in this list.
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedEvent challenge) {
        // Scores are given only for the correct attempts.
        if (challenge.isCorrect()) {
            log.info("Processing correct attempt: {}", challenge);
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);
            List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(
                    scoreCard.getScore(),
                    badgeCards.stream().map(BadgeCard::getType).collect(Collectors.toList())
            );
        } else {
            return new GameResult(0, List.of());
        }
    }

    /**
     * Checks total score and the different scorecards obtained
     * to give new badges in case their conditions are met.
     *
     * @param solvedChallenge solved challenge.
     * @return list of awarded badges.
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedEvent solvedChallenge) {
        String userId = solvedChallenge.getUserId();

        // Gets total score for a user.
        // Returns empty list if user doesn't exist.
        Optional<Integer> optTotalScore = sumTotalScoreForUser(userId);
        if (optTotalScore.isEmpty()) {
            return List.of();
        }
        int totalScore = optTotalScore.get();

        // Gets all the scorecards and existing badges for the user.
        List<ScoreCard> scoreCards = scoreRepository.findByUserIdOrderByTimestampDesc(userId);
        Set<BadgeType> alreadyAwardedBadges = badgeRepository.findByUserIdOrderByTimestampDesc(userId).stream()
                .map(BadgeCard::getType)
                .collect(Collectors.toSet());

        // Calls badge processors for the badges that the user doesn't have yet.
        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyAwardedBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCards, solvedChallenge))
                .flatMap(Optional::stream) // returns an empty stream if empty
                .map(badgeType -> new BadgeCard(userId, badgeType)) // maps the optionals if present to new BadgeCards
                .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);

        return newBadgeCards;
    }

    /**
     * @return total score for a given user, or empty {@code Optional} if user doesn't exist.
     */
    private Optional<Integer> sumTotalScoreForUser(final String userId) {
        AggregationResults<UserTotalScore> aggregationResult = scoreRepository.sumTotalScoreForUser(userId);
        return Optional.ofNullable(aggregationResult.getUniqueMappedResult())
                .map(UserTotalScore::getTotalScore);
    }

}