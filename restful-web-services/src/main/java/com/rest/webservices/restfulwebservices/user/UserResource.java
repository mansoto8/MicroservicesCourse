package com.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservices.restfulwebservices.post.Post;
import com.rest.webservices.restfulwebservices.post.PostDaoService;
import com.rest.webservices.restfulwebservices.post.PostNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@Autowired
	private PostDaoService postDaoService;
	
	@GetMapping(path="users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}
	
	@GetMapping(path="users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		if(user==null)
			throw new UserNotFoundException("id-"+id);
		
		EntityModel<User> model = new EntityModel<>(user);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		
		return model;
	}
	
	@PostMapping(path="users")
	public ResponseEntity<Object> createUser( @Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+id);		
	}
	
	@GetMapping(path = "users/{userId}/posts")
	public List<Post> retrieveUserPosts(@PathVariable int userId) {
		User user = userDaoService.findOne(userId);
		List<Post> userPosts = null;
		if (user == null) {
			throw new UserNotFoundException("id-" + userId);
		}
		
		userPosts = postDaoService.findByUserId(userId);

		return userPosts;
	}
	
	@PostMapping(path = "users/{userId}/posts")
	public ResponseEntity<Object> retrieveUserPosts(@PathVariable int userId, @RequestBody Post post) {
		User user = userDaoService.findOne(userId);

		if (user == null) {
			throw new UserNotFoundException("id-" + userId);
		}

		post.setUserId(userId);
		Post savedPost = postDaoService.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path = "users/{userId}/posts/{postId}")
	public Post retrievePost(@PathVariable int userId, @PathVariable int postId) {
		User user = userDaoService.findOne(userId);
		if (user == null) {
			throw new UserNotFoundException("id-" + userId);
		}
		
		Post post = postDaoService.findById(postId);
		if(post==null) {
			throw new PostNotFoundException("id-" + userId);
		}

		return post;
	}
}
