package org.example.cloud;

import org.example.log.ILogger;
import org.example.log.LogType;

import java.util.HashMap;
import java.util.Map;

public class VmRegistry {

    private final Map<String, VmProfile> cache = new HashMap<>();
    private final ILogger logger;

    public VmRegistry(ILogger logger) {
        this.logger = logger;
    }

    public void addBaseProfile(String key, VmProfile profile) {
        cache.put(key, profile);
        logger.log(LogType.SYSTEM, "Base VM Profile added to registry: " + key);
    }

    public VmProfile provisionVm(String key, String newName) {
        VmProfile original = cache.get(key);

        if (original == null) {
            logger.log(LogType.ERROR, "VM Profile not found: " + key);
            throw new IllegalArgumentException("Profile not found in registry");
        }

        logger.log(LogType.SYSTEM, "Provisioning new VM by cloning profile: " + key);
        VmProfile newVm = original.clonePrototype();
        newVm.setName(newName);

        return newVm;
    }
}