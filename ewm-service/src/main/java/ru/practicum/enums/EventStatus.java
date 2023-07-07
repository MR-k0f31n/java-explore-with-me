package ru.practicum.enums;

/**
 * @author MR.k0F31n
 */
public enum EventStatus {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static EventStatus from(String text) {
        for (EventStatus status : EventStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
