package com.anas.firebaseviews;

public class model {

    String Name,Course,Email,Pimage;

    public model(){


    }


    public model(String name, String course, String email, String pimage) {
        Name = name;
        Course = course;
        Email = email;
        Pimage = pimage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPimage() {
        return Pimage;
    }

    public void setPimage(String pimage) {
        Pimage = pimage;
    }


}
