package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.BadgeCard;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import learn.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    // Spring injects all the @Compnent beans in this list.
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedDto challenge) {
        // Points are given only for the correct attempts.
        if (challenge.isCorrect()) {
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
     * @param solvedChallenge solved challenge
     * @return list of awarded badges
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedDto solvedChallenge) {
        String userId = solvedChallenge.getUserId();

        // Gets total score for a user.
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(userId);
        if (optTotalScore.isEmpty()) {
            return List.of();
        }
        int totalScore = optTotalScore.get();

        // Gets all the scorecards and existing badges for a user.
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

}