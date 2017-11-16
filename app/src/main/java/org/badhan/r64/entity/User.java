package org.badhan.r64.entity;

public class User extends Cadre{
    private int id;
    private boolean isLoggedIn;
    private boolean isGuest;
    private boolean hasPassword;

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

}
