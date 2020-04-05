package com.rest.webservices.restfulwebservices.post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PostDaoService {
	private static List<Post> posts = new ArrayList<>();
	
	static {
		posts.add(new Post(Post.getNextPostId(), 1, "Post user 1"));
		posts.add(new Post(Post.getNextPostId(), 1, "Post 2 user 1"));
		posts.add(new Post(Post.getNextPostId(), 2, "Post user 2"));
		posts.add(new Post(Post.getNextPostId(), 3, "Post user 3"));
	}
	
	public List<Post> findAll() {
		return posts;
	}
	
	public List<Post> findByUserId(int userId) {
		return posts.stream().filter(post -> post.getUserId().equals(userId)).collect(Collectors.toList());
	}
	
	public Post findById(int postId) {
		return posts.stream().filter(post -> post.getId().equals(postId)).findFirst().orElse(null);
	}
	
	public Post save(Post post) {
		if (post.getId() == null) {
			post.setId(Post.getNextPostId());
		}
		posts.add(post);
		return post;
	}
	
	public Post findOne(int id) {
		for(Post post : posts) {
			if(post.getId().equals(id)) {
				return post;
			}
		}
		
		return null;
	}
}
