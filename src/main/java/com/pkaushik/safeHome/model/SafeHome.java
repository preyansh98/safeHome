package com.pkaushik.safeHome.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the container class for the domain.
 * Should be singleton
 */
public class SafeHome {

	private static List<Walker> walkers; 
	private static List<User> users; 
	private static List<Student> students;
	private static SafeHome safeHome_instance; 
	
	
	private SafeHome(){
		walkers = new ArrayList<Walker>();
		users = new ArrayList<User>();
		students = new ArrayList<Student>();
	}

	public static SafeHome getSafeHomeInstance(){
		if(safeHome_instance == null){
			safeHome_instance = new SafeHome(); 
			return safeHome_instance; 
		}
		else{
			return safeHome_instance;
		}
	}
	
	public void removeRole(UserRole userRole) {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		users = new ArrayList<User>(); 
	}

	public void removeUser(User user) {
		users.remove(user); 
	}

	public void addUser(User user) { 
		users.add(user); 
	}

	public List<User> getUsers(){
		return users; 
	}

	public void removeWalker(Walker walker) {
		walkers.remove(walker); 
	}

	public void addWalker(Walker walker) {
		walkers.add(walker); 
	}

	public List<Walker> getWalkers(){
		return walkers; 
	}

	public void removeStudent(Student student){
		students.remove(student); 
	}

	public void addStudent(Student student){
		students.add(student); 
	}

	public List<Student> getStudents(){
		return students; 
	}

}
