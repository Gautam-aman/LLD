package com.cfs.datingapp;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

interface NotificationObserver {
    void update(String msg);
}

class UserNotificationObserver implements NotificationObserver {
    private int userId;

    public UserNotificationObserver(String userId) {
        this.userId = Integer.parseInt(userId.replaceAll("\\D", ""));
    }

    public void update(String msg) {
        System.out.println(msg);
    }
}

class NotificationService {
    private Map<String, NotificationObserver> observers;
    private static NotificationService instance;

    private NotificationService() {
        observers = new ConcurrentHashMap<>();
    }

    public static synchronized NotificationService getInstance() {
        if (instance == null) instance = new NotificationService();
        return instance;
    }

    public void registerObserver(String userId, NotificationObserver observer) {
        observers.put(userId, observer);
    }

    public void unregisterObserver(String userId) {
        observers.remove(userId);
    }

    public void notifyUser(String userId, String msg) {
        NotificationObserver observer = observers.get(userId);
        if (observer != null) observer.update(msg);
    }
}

enum Gender {
    MALE,
    FEMALE,
    NON_BINARY,
    OTHER
}

class Location {
    private double latitude;
    private double longitude;

    public Location() {
        this(0, 0);
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double distanceInKm(Location other) {
        final double earthRadiusKm = 6371.0;
        double dLat = Math.toRadians(other.latitude - latitude);
        double dLon = Math.toRadians(other.longitude - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return earthRadiusKm * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}

class Interest {
    private String name;
    private String category;

    public Interest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
}

class Preference {
    private List<Gender> interestedIn = new ArrayList<>();
    private int minAge = 18;
    private int maxAge = 100;
    private double maxDistance = 100;

    public void addGenderPreference(Gender gender) {
        interestedIn.add(gender);
    }

    public void setAgeRange(int min, int max) {
        minAge = min;
        maxAge = max;
    }

    public void setMaxDistance(double distance) {
        maxDistance = distance;
    }

    public boolean isInterestedInGender(Gender gender) {
        return interestedIn.contains(gender);
    }

    public boolean isAgeInRange(int age) {
        return age >= minAge && age <= maxAge;
    }

    public boolean isDistanceAcceptable(double distance) {
        return distance <= maxDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }
}

class Message {
    private String senderId;
    private String content;
    private long timestamp;

    public Message(String senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getSenderId() { return senderId; }
    public String getContent() { return content; }

    public String getFormattedTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }
}

class ChatRoom {
    private String id;
    private List<String> participants = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public ChatRoom(String id, String u1, String u2) {
        this.id = id;
        participants.add(u1);
        participants.add(u2);
    }

    public boolean hasParticipant(String userId) {
        return participants.contains(userId);
    }

    public void addMessage(String sender, String msg) {
        messages.add(new Message(sender, msg));
    }

    public void display() {
        for (Message m : messages) {
            System.out.println("[" + m.getFormattedTime() + "] " + m.getSenderId() + ": " + m.getContent());
        }
    }
}

class UserProfile {
    private String name;
    private int age;
    private Gender gender = Gender.OTHER;
    private String bio;
    private List<String> photos = new ArrayList<>();
    private List<Interest> interests = new ArrayList<>();
    private Location location = new Location();

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setBio(String bio) { this.bio = bio; }
    public void addPhoto(String photo) { photos.add(photo); }
    public void addInterest(String name, String category) { interests.add(new Interest(name, category)); }
    public void setLocation(Location location) { this.location = location; }

    public String getName() { return name; }
    public int getAge() { return age; }
    public Gender getGender() { return gender; }
    public List<Interest> getInterests() { return interests; }
    public Location getLocation() { return location; }
}

enum SwipeAction {
    LEFT,
    RIGHT
}

class User {
    private String id;
    private UserProfile profile = new UserProfile();
    private Preference preference = new Preference();
    private Map<String, SwipeAction> swipes = new HashMap<>();

    public User(String id) {
        this.id = id;
        NotificationService.getInstance().registerObserver(id, new UserNotificationObserver(id));
    }

    public String getId() { return id; }
    public UserProfile getProfile() { return profile; }
    public Preference getPreference() { return preference; }

    public void swipe(String other, SwipeAction action) {
        swipes.put(other, action);
    }

    public boolean liked(String other) {
        return swipes.getOrDefault(other, SwipeAction.LEFT) == SwipeAction.RIGHT;
    }

    public boolean interacted(String other) {
        return swipes.containsKey(other);
    }
}

interface Matcher {
    double score(User a, User b);
}

class LocationBasedMatcher implements Matcher {
    public double score(User a, User b) {
        if (!a.getPreference().isInterestedInGender(b.getProfile().getGender())) return 0;
        if (!b.getPreference().isInterestedInGender(a.getProfile().getGender())) return 0;
        if (!a.getPreference().isAgeInRange(b.getProfile().getAge())) return 0;
        if (!b.getPreference().isAgeInRange(a.getProfile().getAge())) return 0;
        double d = a.getProfile().getLocation().distanceInKm(b.getProfile().getLocation());
        if (!a.getPreference().isDistanceAcceptable(d) || !b.getPreference().isDistanceAcceptable(d)) return 0;
        return 1;
    }
}

class DatingApp {
    private static DatingApp instance;
    private List<User> users = new ArrayList<>();
    private List<ChatRoom> rooms = new ArrayList<>();
    private Matcher matcher = new LocationBasedMatcher();

    private DatingApp() {}

    public static synchronized DatingApp getInstance() {
        if (instance == null) instance = new DatingApp();
        return instance;
    }

    public User createUser(String id) {
        User u = new User(id);
        users.add(u);
        return u;
    }

    public User getUser(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean swipe(String u1, String u2, SwipeAction action) {
        User a = getUser(u1);
        User b = getUser(u2);
        if (a == null || b == null) return false;

        a.swipe(u2, action);
        if (action == SwipeAction.RIGHT && b.liked(u1)) {
            String id = u1.compareTo(u2) < 0 ? u1 + "_" + u2 : u2 + "_" + u1;
            rooms.add(new ChatRoom(id, u1, u2));
            NotificationService.getInstance().notifyUser(u1, "New match with " + b.getProfile().getName());
            NotificationService.getInstance().notifyUser(u2, "New match with " + a.getProfile().getName());
            return true;
        }
        return false;
    }

    public void sendMessage(String from, String to, String msg) {
        for (ChatRoom r : rooms) {
            if (r.hasParticipant(from) && r.hasParticipant(to)) {
                r.addMessage(from, msg);
                NotificationService.getInstance().notifyUser(to, "New message from " + from);
            }
        }
    }

    public void displayChat(String u1, String u2) {
        for (ChatRoom r : rooms) {
            if (r.hasParticipant(u1) && r.hasParticipant(u2)) {
                r.display();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DatingApp app = DatingApp.getInstance();

        User u1 = app.createUser("user1");
        u1.getProfile().setName("Rohan");
        u1.getProfile().setAge(28);
        u1.getProfile().setGender(Gender.MALE);
        u1.getProfile().addInterest("Coding", "Tech");
        u1.getProfile().setLocation(new Location(1.01, 1.02));
        u1.getPreference().addGenderPreference(Gender.FEMALE);
        u1.getPreference().setAgeRange(25, 30);
        u1.getPreference().setMaxDistance(10);

        User u2 = app.createUser("user2");
        u2.getProfile().setName("Neha");
        u2.getProfile().setAge(27);
        u2.getProfile().setGender(Gender.FEMALE);
        u2.getProfile().addInterest("Travel", "Lifestyle");
        u2.getProfile().setLocation(new Location(1.03, 1.04));
        u2.getPreference().addGenderPreference(Gender.MALE);
        u2.getPreference().setAgeRange(25, 30);
        u2.getPreference().setMaxDistance(15);

        app.swipe("user1", "user2", SwipeAction.RIGHT);
        app.swipe("user2", "user1", SwipeAction.RIGHT);

        app.sendMessage("user1", "user2", "Hi Neha");
        app.sendMessage("user2", "user1", "Hi Rohan");

        app.displayChat("user1", "user2");
    }
}
