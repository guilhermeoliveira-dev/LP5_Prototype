package org.example.cloud;

import java.util.ArrayList;
import java.util.List;

public class VmProfile implements IPrototype<VmProfile> {

    private String name;
    private final String operatingSystem;
    private final int ramGb;
    private List<String> firewallRules;

    public VmProfile(String name, String operatingSystem, int ramGb) {
        this.name = name;
        this.operatingSystem = operatingSystem;
        this.ramGb = ramGb;
        this.firewallRules = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public int getRamGb() {
        return ramGb;
    }

    public List<String> getFirewallRules() {
        return firewallRules;
    }

    public void setFirewallRules(List<String> firewallRules) {
        this.firewallRules = firewallRules;
    }

    public void addFirewallRule(String rule) {
        this.firewallRules.add(rule);
    }

    @Override
    public VmProfile clonePrototype() {
        VmProfile clone = new VmProfile(this.name + " - Clone", this.operatingSystem, this.ramGb);

        clone.setFirewallRules(new ArrayList<>(this.firewallRules));

        return clone;
    }
}