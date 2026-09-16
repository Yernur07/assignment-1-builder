package com.smarthome;
public class SmartHomeSystem {
    private final String systemId;
    private final String ownerName;
    private final String environment;
    private final RoomLocation primaryLocation;
    private final int maxDeviceCapacity;
    private final boolean securityAlarmEnabled;
    private final boolean automaticBackupEnabled;
    private final boolean remoteAccessAllowed;
    private final double temperatureThreshold;
    private final String notificationChannel;
    private SmartHomeSystem(Builder builder) {
        this.systemId = builder.systemId;
        this.ownerName = builder.ownerName;
        this.environment = builder.environment;
        this.primaryLocation = builder.primaryLocation;
        this.maxDeviceCapacity = builder.maxDeviceCapacity;
        this.securityAlarmEnabled = builder.securityAlarmEnabled;
        this.automaticBackupEnabled = builder.automaticBackupEnabled;
        this.remoteAccessAllowed = builder.remoteAccessAllowed;
        this.temperatureThreshold = builder.temperatureThreshold;
        this.notificationChannel = builder.notificationChannel;
    }
    public String getSystemId() { return systemId; }
    public String getOwnerName() { return ownerName; }
    public String getEnvironment() { return environment; }
    public RoomLocation getPrimaryLocation() { return primaryLocation; }
    public int getMaxDeviceCapacity() { return maxDeviceCapacity; }
    public boolean isSecurityAlarmEnabled() { return securityAlarmEnabled; }
    public boolean isAutomaticBackupEnabled() { return automaticBackupEnabled; }
    public boolean isRemoteAccessAllowed() { return remoteAccessAllowed; }
    public double getTemperatureThreshold() { return temperatureThreshold; }
    public String getNotificationChannel() { return notificationChannel; }
    @Override
    public String toString() {
        return "SmartHomeSystem{" +
                "systemId='" + systemId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", environment='" + environment + '\'' +
                ", primaryLocation=" + primaryLocation +
                ", maxDeviceCapacity=" + maxDeviceCapacity +
                ", securityAlarmEnabled=" + securityAlarmEnabled +
                ", automaticBackupEnabled=" + automaticBackupEnabled +
                ", remoteAccessAllowed=" + remoteAccessAllowed +
                ", temperatureThreshold=" + temperatureThreshold +
                ", notificationChannel='" + notificationChannel + '\'' +
                '}';
    }
    public static class Builder {
        private final String systemId;
        private final String ownerName;
        private final String environment;
        private final RoomLocation primaryLocation;
        private int maxDeviceCapacity = 10;
        private boolean securityAlarmEnabled = false;
        private boolean automaticBackupEnabled = false;
        private boolean remoteAccessAllowed = false;
        private double temperatureThreshold = 22.0;
        private String notificationChannel = "EMAIL";
        public Builder(String systemId, String ownerName, String environment, RoomLocation primaryLocation) {
            this.systemId = systemId;
            this.ownerName = ownerName;
            this.environment = environment;
            this.primaryLocation = primaryLocation;
        }
        public Builder maxCapacity(int capacity) {
            this.maxDeviceCapacity = capacity;
            return this;
        }
        public Builder enableSecurityAlarm() {
            this.securityAlarmEnabled = true;
            return this;
        }
        public Builder enableAutomaticBackup() {
            this.automaticBackupEnabled = true;
            return this;
        }
        public Builder allowRemoteAccess() {
            this.remoteAccessAllowed = true;
            return this;
        }
        public Builder setTemperatureThreshold(double temp) {
            this.temperatureThreshold = temp;
            return this;
        }
        public Builder sendNotificationsVia(String channel) {
            this.notificationChannel = channel;
            return this;
        }
        public SmartHomeSystem build() {
            validate();
            return new SmartHomeSystem(this);
        }
        private void validate() {
            if (systemId == null || systemId.isBlank()) {
                throw new IllegalStateException("System ID cannot be empty.");
            }
            if (maxDeviceCapacity <= 0 || maxDeviceCapacity > 500) {
                throw new IllegalStateException("Capacity must be between 1 and 500.");
            }
            if (temperatureThreshold < -10.0 || temperatureThreshold > 50.0) {
                throw new IllegalStateException("Temperature threshold out of safe range (-10 to 50 C).");
            }
            if ("PRODUCTION".equalsIgnoreCase(environment)) {
                if (!securityAlarmEnabled || !automaticBackupEnabled) {
                    throw new IllegalStateException("PRODUCTION environment requires security alarm and automatic backup enabled.");
                }
            }
            if (remoteAccessAllowed && !securityAlarmEnabled) {
                throw new IllegalStateException("Remote access cannot be allowed without enabling the security alarm.");
            }
        }
    }
}