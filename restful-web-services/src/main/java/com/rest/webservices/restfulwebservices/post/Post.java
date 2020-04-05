package com.rest.webservices.restfulwebservices.post;

public class Post {
	
	private static int previousId = 0;
	
	private Integer id;
	private Integer userId;
	private String message;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(Integer id, Integer userId, String message) {
		super();
		this.id = id;
		this.userId = userId;
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static synchronized int getNextPostId() {
		return ++previousId;
	}
}
