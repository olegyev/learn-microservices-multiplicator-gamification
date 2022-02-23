package learn.microservices.gamification.game.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.time.Duration;

/**
 * Configures RabbitMQ via AMQP abstraction.
 */
@Configuration
public class AmqpConfiguration {

    // Configure exchange (to create if not done yet by the publisher).
    @Bean
    public TopicExchange challengesTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    // Configure queue.
    @Bean
    public Queue gamificationQueue(@Value("${amqp.queue.gamification}") final String queueName) {
        return QueueBuilder
                // Don't remove queue on broker shutdown.
                .durable(queueName)
                // Remove messages from a queue after 15 minutes.
                .ttl((int) Duration.ofMinutes(15).toMillis())
                // Maximum 5 messages in a queue at one time.
                .maxLength(5)
                .build();
    }

    // Configure binding key.
    @Bean
    public Binding correctAttemptsBinding(final Queue gamificationQueue,
                                          final TopicExchange attemptsExchange) {
        return BindingBuilder
                .bind(gamificationQueue)
                .to(attemptsExchange)
                .with("attempt.correct");
    }

    // Configure message deserialization from JSON to Java classes.
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    // Configure listener.
    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(final MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return c -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }

}