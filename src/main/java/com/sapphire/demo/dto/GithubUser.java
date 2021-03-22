package com.sapphire.demo.dto;

public class GithubUser {
    private String name;
    private String login;
    private Long id;
    private String bio;
    private String avatar_url;
    private String email;

    
    public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
	}


	public String getAvatar_url() {
		return avatar_url;
	}


	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
