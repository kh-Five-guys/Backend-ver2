package com.finalproject.service;

import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ParticipantService {
    private static Set<String> participants = ConcurrentHashMap.newKeySet();

    public static void addParticipant(String username) {
        participants.add(username);
    }

    public void removeParticipant(String username) {
        participants.remove(username);
    }

    public Set<String> getParticipants() {
        return participants;
    }
}
