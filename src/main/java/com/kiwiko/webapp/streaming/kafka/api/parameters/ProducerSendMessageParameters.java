package com.kiwiko.webapp.streaming.kafka.api.parameters;

public class ProducerSendMessageParameters {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final String topicName;
    private final String message;
    private final OccurrenceType occurrenceType;

    private ProducerSendMessageParameters(
            String topicName,
            String message,
            OccurrenceType occurrenceType) {
        this.topicName = topicName;
        this.message = message;
        this.occurrenceType = occurrenceType;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getMessage() {
        return message;
    }

    public OccurrenceType getOccurrenceType() {
        return occurrenceType;
    }

    @Override
    public String toString() {
        return "ProducerSendMessageParameters{" +
                "topicName='" + topicName + '\'' +
                ", message='" + message + '\'' +
                ", occurrenceType=" + occurrenceType +
                '}';
    }

    public static class Builder {
        private String topicName;
        private String message;
        private OccurrenceType occurrenceType = OccurrenceType.SYNCRHONOUS;

        public Builder setTopicName(String topicName) {
            this.topicName = topicName;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setOccurrenceType(OccurrenceType occurrenceType) {
            this.occurrenceType = occurrenceType;
            return this;
        }

        public ProducerSendMessageParameters build() {
            return new ProducerSendMessageParameters(
                    topicName,
                    message,
                    occurrenceType);
        }
    }
}
