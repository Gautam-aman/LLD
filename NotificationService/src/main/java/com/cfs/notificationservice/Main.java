package com.cfs.notificationservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


interface Inotification {
    String getcontent();
}


class SimpleNotification implements Inotification {
    String message;

    public SimpleNotification(String message) {
        this.message = message;
    }

    @Override
    public String getcontent() {
        return message;
    }
}


abstract class NotificationDecorator implements Inotification {
    protected Inotification inotification;

    public NotificationDecorator(Inotification inotification) {
        this.inotification = inotification;
    }
}


class NotificationWithDate extends NotificationDecorator {

    public NotificationWithDate(Inotification inotification) {
        super(inotification);
    }

    @Override
    public String getcontent() {
        return inotification.getcontent() + " on " + LocalDateTime.now();
    }
}


class NotificationWithJoke extends NotificationDecorator {

    public NotificationWithJoke(Inotification inotification) {
        super(inotification);
    }

    @Override
    public String getcontent() {
        return inotification.getcontent() + " with a Joke 😂";
    }
}


class NotificationWithSignature extends NotificationDecorator {

    private String sign;

    public NotificationWithSignature(Inotification inotification, String sign) {
        super(inotification);
        this.sign = sign;
    }

    @Override
    public String getcontent() {
        return inotification.getcontent() + " - " + sign;
    }
}


interface Observers {
    void update();
}

interface Observable {
    void notifyObservers();
    void addObserver(Observers observer);
    void removeObserver(Observers observer);
}

abstract class NotificationObservable implements Observable {

    protected Inotification inotification;
    protected List<Observers> observers = new ArrayList<>();

    @Override
    public void addObserver(Observers observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observers observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observers obs : observers) {
            obs.update();
        }
    }

    public void setNotification(Inotification inotification) {
        this.inotification = inotification;
        notifyObservers();
    }

    public Inotification getNotification() {
        return inotification;
    }

    public String getNotificationContent() {
        return inotification.getcontent();
    }
}

// concrete observable
class DefaultNotificationObservable extends NotificationObservable {
}



class Logger implements Observers {

    NotificationObservable observable;

    public Logger(NotificationObservable observable) {
        this.observable = observable;
    }

    @Override
    public void update() {
        String content = observable.getNotificationContent();
        System.out.println("[LOG] New notification received: " + content);
    }
}


interface NotificationStrategy {
    void notifyObservers(String content);
}

class EmailStrategy implements NotificationStrategy {
    private String email;

    public EmailStrategy(String email) {
        this.email = email;
    }

    @Override
    public void notifyObservers(String content) {
        System.out.println("EMAIL to " + email + ": " + content);
    }
}

class SMSStrategy implements NotificationStrategy {
    private String number;

    public SMSStrategy(String number) {
        this.number = number;
    }

    @Override
    public void notifyObservers(String content) {
        System.out.println("SMS to " + number + ": " + content);
    }
}


class NotificationEngine implements Observers {

    NotificationObservable observable;
    List<NotificationStrategy> strategies = new ArrayList<>();

    public NotificationEngine(NotificationObservable observable) {
        this.observable = observable;
    }

    public void addNotificationStrategy(NotificationStrategy strategy) {
        strategies.add(strategy);
    }

    @Override
    public void update() {
        String content = observable.getNotificationContent();
        for (NotificationStrategy strategy : strategies) {
            strategy.notifyObservers(content);
        }
    }
}



class NotificationService {

    private NotificationObservable observable;
    private List<Inotification> notifications = new ArrayList<>();

    private static NotificationService instance;

    private NotificationService() {
        observable = new DefaultNotificationObservable();
    }

    public static NotificationService getInstance() {
        if (instance == null)
            instance = new NotificationService();
        return instance;
    }

    public NotificationObservable getNotificationObservable() {
        return observable;
    }

    public void sendNotification(Inotification notif) {
        notifications.add(notif);
        observable.setNotification(notif);
    }
}



public class Main {
    public static void main(String[] args) {

        NotificationService service = NotificationService.getInstance();
        NotificationObservable observable = service.getNotificationObservable();

        // Observers
        Logger logger = new Logger(observable);
        NotificationEngine engine = new NotificationEngine(observable);

        // Strategies
        engine.addNotificationStrategy(new EmailStrategy("test@gmail.com"));
        engine.addNotificationStrategy(new SMSStrategy("9999999999"));

        // Subscribe
        observable.addObserver(logger);
        observable.addObserver(engine);

        // Create Notification with Decorators
        Inotification notification =
                new SimpleNotification("Hello World");

        notification = new NotificationWithJoke(notification);
        notification = new NotificationWithSignature(notification, "Aman");
        notification = new NotificationWithDate(notification);

        // Send Notification
        service.sendNotification(notification);
    }
}
