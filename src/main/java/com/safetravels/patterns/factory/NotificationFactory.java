package com.safetravels.patterns.factory;

import com.safetravels.enums.NotificationChannel;
import com.safetravels.patterns.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationFactory {

    private final List<NotificationStrategy> strategies;

    public NotificationStrategy getStrategy(NotificationChannel channel) {
        Map<String, NotificationStrategy> strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        NotificationStrategy::getChannelType,
                        Function.identity()));
        return strategyMap.getOrDefault(channel.name(), strategies.get(0));
    }
}
