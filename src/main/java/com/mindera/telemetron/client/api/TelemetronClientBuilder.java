package com.mindera.telemetron.client.api;

public final class TelemetronClientBuilder {
    private final ConfigurationBuilder<TelemetronClient> configurationBuilder;

    public TelemetronClientBuilder(final ConfigurationBuilder<TelemetronClient> configurationBuilder) {
        this.configurationBuilder = configurationBuilder;
    }

    public ConfigurationBuilder<TelemetronClient> with() {
        return configurationBuilder;
    }

    public TelemetronClient build() {
        return configurationBuilder.build();
    }
}
