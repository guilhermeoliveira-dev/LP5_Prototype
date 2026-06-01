package org.example.cloud;

import org.example.log.LogType;
import org.example.log.MockLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VmProvisioningTest {

    private MockLogger spyLogger;
    private VmRegistry registry;

    @BeforeEach
    void setUp() {
        spyLogger = MockLogger.get();
        spyLogger.clearLogs();
        registry = new VmRegistry(spyLogger);
    }

    @Test
    void givenBaseProfile_whenProvisionVm_thenCreatesExactCloneWithNewName() {
        VmProfile baseProfile = new VmProfile("Base-Ubuntu", "Ubuntu 22.04", 16);
        baseProfile.addFirewallRule("Allow Port 80");
        baseProfile.addFirewallRule("Allow Port 443");
        registry.addBaseProfile("web-server", baseProfile);

        VmProfile clonedVm = registry.provisionVm("web-server", "Prod-Web-01");

        assertNotSame(baseProfile, clonedVm);
        assertEquals("Prod-Web-01", clonedVm.getName());
        assertEquals("Ubuntu 22.04", clonedVm.getOperatingSystem());
        assertEquals(16, clonedVm.getRamGb());
        assertEquals(2, clonedVm.getFirewallRules().size());

        assertTrue(spyLogger.containsLog("Base VM Profile added to registry: web-server"));
        assertTrue(spyLogger.containsLog("Provisioning new VM by cloning profile: web-server"));
    }

    @Test
    void givenClonedVm_whenModifyingNestedList_thenOriginalProfileRemainsUnchanged() {
        VmProfile baseProfile = new VmProfile("Base-DB", "PostgreSQL-Linux", 32);
        baseProfile.addFirewallRule("Allow Port 5432");
        registry.addBaseProfile("db-server", baseProfile);

        VmProfile clonedVm = registry.provisionVm("db-server", "Prod-DB-01");

        clonedVm.addFirewallRule("Allow IP 10.0.0.5");

        assertEquals(2, clonedVm.getFirewallRules().size());
        assertEquals(1, baseProfile.getFirewallRules().size());
        assertFalse(baseProfile.getFirewallRules().contains("Allow IP 10.0.0.5"));
    }

    @Test
    void givenInvalidProfileKey_whenProvisionVm_thenThrowsExceptionAndLogsError() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.provisionVm("non-existent-profile", "Test-VM");
        });

        assertEquals("Profile not found in registry", exception.getMessage());
        assertTrue(spyLogger.containsLog("VM Profile not found: non-existent-profile"));
    }
}