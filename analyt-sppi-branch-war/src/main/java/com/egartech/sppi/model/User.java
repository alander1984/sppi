package com.egartech.sppi.model;

import javax.persistence.*;

@Entity
@Table(name = "sppi_users")
public class User {

    public User() {}

    public User(String username, String userFIO, String userPersonnelNum, String password) {
        this.username = username;
        this.password = password;
        this.userFIO = userFIO;
        this.userPersonnelNum = userPersonnelNum;
        this.activated = Boolean.TRUE;
        this.isSelfRegistered = Boolean.TRUE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "passwd", nullable = false)
    private String password;

    @Column(name = "first_password", nullable = false)
    private String firstPassword;
    
    @Column(name="activated")
    private Boolean activated;

    @Column(name = "user_fio")
    private String userFIO;

    @Column(name = "user_personnel_num")
    private String userPersonnelNum;

    @Column(name = "is_self_registered")
    private Boolean isSelfRegistered;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public String getFirstPassword() {
		return firstPassword;
	}

	public void setFirstPassword(String firstPassword) {
		this.firstPassword = firstPassword;
	}

    public String getUserFIO() {
        return userFIO;
    }

    public void setUserFIO(String userFIO) {
        this.userFIO = userFIO;
    }

    public String getUserPersonnelNum() {
        return userPersonnelNum;
    }

    public void setUserPersonnelNum(String userPersonnelNum) {
        this.userPersonnelNum = userPersonnelNum;
    }

    public Boolean getSelfRegistered() {
        return isSelfRegistered;
    }

    public void setSelfRegistered(Boolean selfRegistered) {
        isSelfRegistered = selfRegistered;
    }
}
