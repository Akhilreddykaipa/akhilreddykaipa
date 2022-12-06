package org.rffc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCases {

    GymManager gymManager;

    @BeforeEach
    public void setup() {
        gymManager = new GymManager();
        Map<String, User> adminusersMap = new HashMap<>();
        adminusersMap.put("1", new Admin("1", "admin", "admin", true));
        adminusersMap.put("2", new Admin("2", "admi2", "admin", true));
        gymManager.setUsers(adminusersMap);
        User mem1 = new Member("1m", "mem1", "mem", new BasicPlan());
        User mem2 = new Member("2m", "mem2", "mem", new FoxPlan());
        gymManager.getUsers().put("1m", mem1);
        gymManager.getUsers().put("2m", mem2);
        Gym gym = new Gym();
        HashMap<Gym, Map<String, Member>> m = new HashMap<>();
        m.put(gym, new HashMap<>());
        gymManager.setGyms(m);
        gymManager.getGyms().get(gym).put("1m", (Member) mem1);
        gymManager.getGyms().get(gym).put("2m", (Member) mem2);

    }

    @Test
    public void testAdminLogin() throws LoginException {
        boolean result = gymManager.doAdminLogin("1", "admin");
        Assertions.assertEquals(true, result);
    }

    @Test
    public void testMemberLogin() throws LoginException {
        boolean result = gymManager.doMemberLogin("1m", "mem");
        Assertions.assertEquals(true, result);
    }

    @Test()
    public void testFailedMemberLogin() {
        try {
            boolean result = gymManager.doMemberLogin("1m", "mem1");
        } catch (LoginException e) {
            Assertions.assertNotNull(e);
        }


    }

    @Test()
    public void testFailedAdminLogin() {
        try {
            boolean result = gymManager.doAdminLogin("1", "admin1");
        } catch (LoginException e) {
            Assertions.assertNotNull(e);
        }


    }

    @Test()
    public void testAddEquipentsCount() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        entry.getKey().getEquipments().put("e2", 100);
        Assertions.assertEquals(5, gymManager.viewEquipments().entrySet().size());
    }

    @Test()
    public void testBasicPlanEnrollMember() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Map<String, Member> members = entry.getValue();
        Assertions.assertTrue(members.get("1m").getPlan() instanceof BasicPlan);
    }

    @Test()
    public void testFoxPlanEnrollMember() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Map<String, Member> members = entry.getValue();
        Assertions.assertTrue(members.get("2m").getPlan() instanceof FoxPlan);
    }


    @Test()
    public void testEnrollClassMember() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Map<String, Member> members = entry.getValue();
        if (members.containsKey("1m")) {
            members.get("1m").getClasses().put("1-1", "monday");
        }
        Assertions.assertEquals("monday", members.get("1m").getClasses().get("1-1"));
    }

    @Test()
    public void testunEnrollClassMember() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Map<String, Member> members = entry.getValue();
        if (members.containsKey("1m")) {
            members.get("1m").getClasses().remove("1-1");
        }
        Assertions.assertEquals(null, members.get("1m").getClasses().get("1-1"));
    }

    @Test()
    public void testGymNotNull() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Assertions.assertNotNull(entry);
    }

    @Test()
    public void testMembers() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Assertions.assertNotNull(entry.getValue());
    }

    @Test()
    public void testMembersCount() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Assertions.assertEquals(2, entry.getValue().size());
    }

    @Test()
    public void testPoints() {

        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        Map<String, Member> members = entry.getValue();
        if (members.containsKey("1m")) {
            members.get("1m").setPoints(10.0);
        }
        Assertions.assertEquals(10.0, gymManager.getPoints("1m"));
    }

    @Test()
    public void testPoints2() {
        Assertions.assertEquals(0.0, gymManager.getPoints("1mm"));
    }


    @Test()
    public void testAddEquipents() {
        Map.Entry<Gym, Map<String, Member>> entry = gymManager.getGyms().entrySet().iterator().next();
        entry.getKey().getEquipments().put("e1", 10);
        Assertions.assertTrue(gymManager.viewEquipments().containsKey("e1"));
    }


}
