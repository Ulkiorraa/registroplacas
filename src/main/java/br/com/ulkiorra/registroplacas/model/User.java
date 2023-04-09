package br.com.ulkiorra.registroplacas.model;

public class User {
    Long id;
    String user;
    String password;

    public User() {
    }

    public User(Long id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
