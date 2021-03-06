package securechat.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sasch on 31/10/2017.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;
    @Column(length=100000)
    private byte[] pwd;
    @Column(length=100000)
    private byte[] salt;
    @Column(length=100000)
    private byte[] lastChallenge;
    private Date lastTokenDate;
    @Column(length=100000)
    private String token;

    protected Account(){}

    public Account(String username, byte[] pwd, byte[] salt, byte[] lastChallenge, String token) {
        this.username = username;
        this.pwd = pwd;
        this.salt = salt;
        this.lastChallenge = lastChallenge;
        this.token = token;
    }

    public Date getLastTokenDate() {
        return lastTokenDate;
    }

    public void setLastTokenDate(Date lastTokenDate) {
        this.lastTokenDate = lastTokenDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPwd() {
        return pwd;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getLastChallenge() {
        return lastChallenge;
    }

    public void setLastChallenge(byte[] lastChallenge) {
        this.lastChallenge = lastChallenge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
