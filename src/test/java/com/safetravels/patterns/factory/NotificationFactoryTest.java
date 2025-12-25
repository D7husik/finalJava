package com.safetravels.patterns.factory;

import com.safetravels.enums.NotificationChannel;
import com.safetravels.patterns.strategy.ConsoleNotificationStrategy;
import com.safetravels.patterns.strategy.InAppNotificationStrategy;
import com.safetravels.patterns.strategy.MockSmsNotificationStrategy;
import com.safetravels.patterns.strategy.NotificationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    private NotificationFactory notificationFactory;

    @BeforeEach
    void setUp() {
        List<NotificationStrategy> strategies = List.of(
                new InAppNotificationStrategy(),
                new ConsoleNotificationStrategy(),
                new MockSmsNotificationStrategy());
        notificationFactory = new NotificationFactory(strategies);
    }

    @Test
    void getStrategy_InApp_ReturnsInAppStrategy() {
        NotificationStrategy strategy = notificationFactory.getStrategy(NotificationChannel.IN_APP);
        assertNotNull(strategy);
        assertInstanceOf(InAppNotificationStrategy.class, strategy);
    }

    @Test
    void getStrategy_Sms_ReturnsSmsStrategy() {
        NotificationStrategy strategy = notificationFactory.getStrategy(NotificationChannel.SMS);
        assertNotNull(strategy);
        assertInstanceOf(MockSmsNotificationStrategy.class, strategy);
    }

    @Test
    void getStrategy_Console_ReturnsConsoleStrategy() {
        NotificationStrategy strategy = notificationFactory.getStrategy(NotificationChannel.CONSOLE);
        assertNotNull(strategy);
        assertInstanceOf(ConsoleNotificationStrategy.class, strategy);
    }

    @Test
    void strategyChannelTypes_AreCorrect() {
        assertEquals("IN_APP", new InAppNotificationStrategy().getChannelType());
        assertEquals("CONSOLE", new ConsoleNotificationStrategy().getChannelType());
        assertEquals("SMS", new MockSmsNotificationStrategy().getChannelType());
    }

    @Test
    void strategies_SendSuccessfully() {
        assertTrue(new InAppNotificationStrategy().send("test@email.com", "Test message"));
        assertTrue(new ConsoleNotificationStrategy().send("test@email.com", "Test message"));
        assertTrue(new MockSmsNotificationStrategy().send("test@email.com", "Test message"));
    }
}
