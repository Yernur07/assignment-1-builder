package com.smarthome;
public class SmartHomeDirector {
    public SmartHomeSystem buildBasicConfig(String id, String owner, RoomLocation location) {
        return new SmartHomeSystem.Builder(id, owner, "DEVELOPMENT", location)
                .maxCapacity(5)
                .setTemperatureThreshold(20.0)
                .build();
    }
    public SmartHomeSystem buildSafeConfig(String id, String owner, RoomLocation location) {
        SmartHomeSystem system = new SmartHomeSystem.Builder(id, owner, "PRODUCTION", location)
                .maxCapacity(50)
                .enableSecurityAlarm()
                .enableAutomaticBackup()
                .sendNotificationsVia("SMS")
                .build();
        System.out.println("SUCCESS: SmartHomeSystem [SAFE] created with ID: " + system.getSystemId());
        return system;
    }
    public SmartHomeSystem buildPerformanceConfig(String id, String owner, RoomLocation location) {
        return new SmartHomeSystem.Builder(id, owner, "PRODUCTION", location)
                .maxCapacity(300)
                .enableSecurityAlarm()
                .enableAutomaticBackup()
                .allowRemoteAccess()
                .sendNotificationsVia("PUSH")
                .setTemperatureThreshold(24.0)
                .build();
    }
}
