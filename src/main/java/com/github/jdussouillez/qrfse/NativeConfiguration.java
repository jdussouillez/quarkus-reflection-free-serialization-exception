package com.github.jdussouillez.qrfse;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(
    targets = {
        EventResource.EventNotFoundException.class,
    }
)
public class NativeConfiguration {
}
