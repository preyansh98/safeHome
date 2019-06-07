package com.pkaushik.safeHome.service;


import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Student;
import com.pkaushik.safeHome.model.User;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;
import com.pkaushik.safeHome.repository.UserRepository;
import com.pkaushik.safeHome.repository.WalkerRepository;
import com.pkaushik.safeHome.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Optional;

//TODO: too many db calls, the less we access db the better.
public class UserAuthService {

    @Autowired
    private WalkerRepository walkerRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private UserRepository userRepo;

    public void regService(BigInteger phoneNo, int id, boolean regAsWlkr) {

        //state valids:
        //1. check if already reg based on regAswlkr

        //already registered in this specific role?
        if (regAsWlkr) {
            if (walkerRepo.existsById(id)) throw new IllegalStateException("exists");
        } else {
            if (studentRepo.existsById(id)) throw new IllegalStateException("exists");
        }


        //2. check if id starts with correct digs

        char[] idDigits = ("" + id).toCharArray();
        if (
                (Character.getNumericValue(idDigits[0]) == 2)
                        && (Character.getNumericValue(idDigits[1]) == 6) &&
                        (Character.getNumericValue(idDigits[2]) == 0)
        ) {
            //mcgill ID is okay
        } else {
            throw new IllegalStateException("McGill ID should start with '260'");
        }

        //2. need to create a user, with user role, and userrole impl
        //at this point the role does not exist, since that exception is taken care of already.

        User user = null;
        Walker walkerRole = null;
        Student studentRole = null;

        if (regAsWlkr) {
            walkerRole = new Walker(SafeHome.getSafeHomeInstance(), false);
        } else {
            studentRole = new Student(SafeHome.getSafeHomeInstance());
        }

        if (userRepo.existsById(id)) {
            user = userRepo.findById(id).get();
        } else {
            //TODO: config user to take in safehome instance
            //TODO: make db so phone and id is unique on construct.
            user = new User(phoneNo, id, SafeHome.getSafeHomeInstance());
        }
        //user exists already, find the id, and add the corresponding role to user
        //we're adding another role to this user.
        System.gc();
        if (regAsWlkr) {
            user.addRole(walkerRole);
            //save walker to walkerrepo
            walkerRepo.save(walkerRole);
        } else {
            user.addRole(studentRole);
            //save student to studentrepo
            studentRepo.save(studentRole);
        }

        userRepo.save(user);
    }
    //User -> User ID, Phone No, Roles should be stored too, way to deserialize that.

    public void loginService(int id, boolean loginAsWalker) {
        //state validation:
        //should be reg

        //does user exist?
        Optional<User> userOp = userRepo.findById(id);

        if (userOp.isPresent()) {

            //check if user role exists.
            //TODO; this is assuming data model in which user pk = student pk = walker pk = mc id.
            if (loginAsWalker) {
                //check if walker exists with id that walker is set with
                if (walkerRepo.findById(id).isPresent()) {
                    //walker exists, set logged in and set status.
                    Walker walker = walkerRepo.findById(id).get();
                    SafeHomeApplication.logInUser(id, walker);
                    walker.setStatus(WalkerStatus.LOGGED_IN);
                    walkerRepo.save(walker);
                } else {
                    //chose to login as a walker, and walker role does not exist
                    throw new IllegalStateException("You are not registered as a Walker");
                }
            } else {
                //check if student exists with id that student is set with
                if (studentRepo.findById(id).isPresent()) {
                    Student student = studentRepo.findById(id).get();
                    SafeHomeApplication.logInUser(id, student);
                }
            }
        } else {
            //user is not reg at all
            throw new IllegalStateException("You are not registered");
        }
    }

    public void logoutService(int id){

        //validation: user must be currently logged in.

        if(!SafeHomeApplication.getCurrentRequestsMap().containsKey(id))
            throw new IllegalStateException("You can not log out without logging in first. ");









        //todo: should we store currentRoleLoggedIn as property for user.
        //use a repository pattern to store a dto in the db.



        //TODO; logout should persist some data from memory into database.
    }
}
