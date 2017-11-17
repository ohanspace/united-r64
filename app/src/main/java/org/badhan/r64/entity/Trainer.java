package org.badhan.r64.entity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Trainer {
    private int id;
    private String name;
    private String designation;
    private String trainingPost;
    private String telephone;
    private String email;
    private String alphabet;

    public StorageReference getAvatarRef(){
        return FirebaseStorage.getInstance()
                .getReference("cmtAvatars")
                .child(Integer.toString(getId()) + ".jpg");
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

    public void setDisplayName(String displayName) {
        this.name = displayName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTrainingPost() {
        return trainingPost;
    }

    public void setTrainingPost(String trainingPost) {
        this.trainingPost = trainingPost;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
