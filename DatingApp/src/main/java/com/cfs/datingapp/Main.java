package com.cfs.datingapp;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

interface NotificationObserver{
    public void update(String msg);
}

class UserNotificationObserver implements NotificationObserver{

    @Override
    public void update(String msg) {
        System.out.println(msg);
    }
}

class NotificationService{
    private Map<String , NotificationObserver> observers;

    private static NotificationService instance = null;
    private NotificationService(){
        observers = new ConcurrentHashMap<>();
    }

    public static NotificationService getInstance(){
        if(instance == null){
            instance = new NotificationService();
        }
        return instance;
    }

    public void registerObserver(String userId , NotificationObserver observer){
        observers.put(userId, observer);
    }

    public void unregisterObserver(String userId){
        observers.remove(userId);
    }

    public void notifyObserver(String msg){
        for(Map.Entry<String, NotificationObserver> entry : observers.entrySet()){
            entry.getValue().update(msg);
        }
    }
}

enum Gender{
    Male , Female , Binary;
}

class Location{
    public double latitude, longitude;
    public Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Location(){
        this.latitude = 0;
        this.longitude = 0;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distanceInKm(Location other) {
        final double earthRadiusKm = 6371.0;
        double dLat = (other.latitude - latitude) * Math.PI / 180.0;
        double dLon = (other.longitude - longitude) * Math.PI / 180.0;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(latitude * Math.PI / 180.0) * Math.cos(other.latitude * Math.PI / 180.0) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadiusKm * c;
    }
}

class Interest{
    private String name;
    private String category;
    public Interest(String name, String category){
        this.name = name;
        this.category = category;
    }
    public Interest(){
        name = "";
        category = "";
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}

class Preference {
    private List<Gender> interestedIn;
    private int minAge;
    private int maxAge;
    private double maxDistance; // in kilometers
    private List<String> interests;

    public Preference() {
        interestedIn = new ArrayList<>();
        interests = new ArrayList<>();
        minAge = 18;
        maxAge = 100;
        maxDistance = 100.0;
    }

    public void addGenderPreference(Gender gender) {
        interestedIn.add(gender);
    }

    public void removeGenderPreference(Gender gender) {
        interestedIn.remove(gender);
    }

    public void setAgeRange(int min, int max) {
        minAge = min;
        maxAge = max;
    }

    public void setMaxDistance(double distance) {
        maxDistance = distance;
    }

    public void addInterest(String interest) {
        interests.add(interest);
    }

    public void removeInterest(String interest) {
        interests.remove(interest);
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

    public List<String> getInterests() {
        return interests;
    }

    public List<Gender> getInterestedGenders() {
        return interestedIn;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getMaxDistance() {
        return maxDistance;
    }
}


class Message{
    private String content;
    private String senderId;
    private Long timestamp;
    public Message(String sender, String msg) {
        senderId = sender;
        content = msg;
        timestamp = System.currentTimeMillis();
    }

    public String getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
}

class ChatRoom {
    private String id;
    private List<String> participantIds;
    private List<Message> messages;

    public ChatRoom(String roomId, String user1Id, String user2Id) {
        id = roomId;
        participantIds = new ArrayList<>();
        participantIds.add(user1Id);
        participantIds.add(user2Id);
        messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addMessage(String senderId, String content) {
        Message msg = new Message(senderId, content);
        messages.add(msg);
    }

    public boolean hasParticipant(String userId) {
        return participantIds.contains(userId);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getParticipants() {
        return participantIds;
    }

    public void displayChat() {
        System.out.println("===== Chat Room: " + id + " =====");
        for (Message msg : messages) {
            System.out.println("[" + msg.getFormattedTime() + "] " + msg.getSenderId() + ": " + msg.getContent());
        }
        System.out.println("=========================");
    }
}

class UserProfile {
    private String name;
    private int age;
    private Gender gender;
    private String bio;
    private List<String> photos;
    private List<Interest> interests;
    private Location location;

    public UserProfile() {
        name = "";
        age = 0;
        gender = Gender.OTHER;
        photos = new ArrayList<>();
        interests = new ArrayList<>();
        location = new Location();
    }

    public void setName(String n) {
        name = n;
    }

    public void setAge(int a) {
        age = a;
    }

    public void setGender(Gender g) {
        gender = g;
    }

    public void setBio(String b) {
        bio = b;
    }

    public void addPhoto(String photoUrl) {
        photos.add(photoUrl);
    }

    public void removePhoto(String photoUrl) {
        photos.remove(photoUrl);
    }

    public void addInterest(String name, String category) {
        Interest interest = new Interest(name, category);
        interests.add(interest);
    }

    public void removeInterest(String name) {
        interests.removeIf(i -> i.getName().equals(name));
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public Location getLocation() {
        return location;
    }

    public void display() {
        System.out.println("===== Profile =====");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.print("Gender: ");
        switch (gender) {
            case MALE:
                System.out.print("Male");
                break;
            case FEMALE:
                System.out.print("Female");
                break;
            case NON_BINARY:
                System.out.print("Non-binary");
                break;
            case OTHER:
                System.out.print("Other");
                break;
        }
        System.out.println();
        System.out.println("Bio: " + bio);
        System.out.print("Photos: ");
        for (String photo : photos) {
            System.out.print(photo + ", ");
        }
        System.out.println();
        System.out.print("Interests: ");
        for (Interest i : interests) {
            System.out.print(i.getName() + " (" + i.getCategory() + "), ");
        }
        System.out.println();
        System.out.println("Location: " + location.getLatitude() + ", " + location.getLongitude());
        System.out.println("===================");
    }
}

enum SwipeAction {
    LEFT,  // Dislike
    RIGHT  // Like
}



public class Main {
}
