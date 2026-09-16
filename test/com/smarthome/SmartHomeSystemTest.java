package com.smarthome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class SmartHomeSystemTest {
    private RoomLocation defaultLocation;
    @BeforeEach
    void setUp() {
        defaultLocation = new RoomLocation("1st Floor", "Living Room");
    }
    @Test
    void testValidBasicBuild() {
        SmartHomeSystem system = new SmartHomeSystem.Builder("SYS-1", "Alice", "DEVELOPMENT", defaultLocation)
                .maxCapacity(15)
                .build();
        assertEquals("SYS-1", system.getSystemId());
        assertEquals(15, system.getMaxDeviceCapacity());
    }
    @Test
    void testValidProductionBuildWithDirector() {
        SmartHomeDirector director = new SmartHomeDirector();
        SmartHomeSystem system = director.buildSafeConfig("SYS-2", "Bob", defaultLocation);
        assertTrue(system.isSecurityAlarmEnabled());
        assertTrue(system.isAutomaticBackupEnabled());
    }
    @Test
    void testValidFullConfiguration() {
        SmartHomeSystem system = new SmartHomeSystem.Builder("SYS-3", "Charlie", "PRODUCTION", defaultLocation)
                .maxCapacity(100)
                .enableSecurityAlarm()
                .enableAutomaticBackup()
                .allowRemoteAccess()
                .setTemperatureThreshold(21.5)
                .sendNotificationsVia("TELEGRAM")
                .build();
        assertEquals("TELEGRAM", system.getNotificationChannel());
    }

    @Test
    void testInvalidCapacityThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-4", "Dave", "DEVELOPMENT", defaultLocation)
                        .maxCapacity(-5)
                        .build()
        );
    }
    @Test
    void testInvalidTemperatureThresholdThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-5", "Eve", "DEVELOPMENT", defaultLocation)
                        .setTemperatureThreshold(100.0)
                        .build()
        );
    }
    @Test
    void testProductionWithoutBackupThrowsException() {
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-6", "Frank", "PRODUCTION", defaultLocation)
                        .enableSecurityAlarm()
                        .build()
        );
    }
    @Test
    void testBoundaryCapacityMaximum() {
        SmartHomeSystem system = new SmartHomeSystem.Builder("SYS-7", "Grace", "DEVELOPMENT", defaultLocation)
                .maxCapacity(500)
                .build();
        assertEquals(500, system.getMaxDeviceCapacity());
    }
    @Test
    void testBoundaryTemperatureMinimum() {
        SmartHomeSystem system = new SmartHomeSystem.Builder("SYS-8", "Heidi", "DEVELOPMENT", defaultLocation)
                .setTemperatureThreshold(-10.0)
                .build();
        assertEquals(-10.0, system.getTemperatureThreshold());
    }
    @Test
    void testRemoteAccessWithoutSecurityAlarmFails() {
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-9", "Ivan", "DEVELOPMENT", defaultLocation)
                        .allowRemoteAccess()
                        .build()
        );
    }
    @Test
    void testBuilderReuseIndependence() {
        SmartHomeSystem.Builder builder = new SmartHomeSystem.Builder("SYS-10", "Judy", "DEVELOPMENT", defaultLocation)
                .maxCapacity(20);
        SmartHomeSystem system1 = builder.build();
        builder.maxCapacity(50);
        SmartHomeSystem system2 = builder.build();
        assertEquals(20, system1.getMaxDeviceCapacity());
        assertEquals(50, system2.getMaxDeviceCapacity());
        assertNotEquals(system1.getMaxDeviceCapacity(), system2.getMaxDeviceCapacity());
    }
}
