package com.team4822.studyup.services.game;

import com.team4822.studyup.models.game.PlayerUser;

import java.util.LinkedList;
import java.util.Queue;

public class SearchQueue {
    private static SearchQueue instance;
    private Queue<PlayerUser> searchingUsers;

    public SearchQueue() {
        searchingUsers = new LinkedList<>();
    }

    public static synchronized SearchQueue getInstance() {
        if (instance == null) {
            System.out.println(33);
            instance = new SearchQueue();
        }
        return instance;
    }

    public Queue<PlayerUser> getSearchingUsers() {
        System.out.println(searchingUsers);
        return searchingUsers;
    }

    public void addUser(PlayerUser playerUser) {
        searchingUsers.add(playerUser);
    }

    public PlayerUser pollUser() {
        return searchingUsers.poll();
    }

    public void removeUser(PlayerUser playerUser) {
        searchingUsers.remove(playerUser);
    }
}
