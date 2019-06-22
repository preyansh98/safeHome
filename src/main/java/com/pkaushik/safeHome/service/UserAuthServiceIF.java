package com.pkaushik.safeHome.service;

import java.math.BigInteger;

public interface UserAuthServiceIF {

    void loginService(int mcgillID, boolean loginAsWalker);

    void registerService(BigInteger phoneNo, int mcgillID, boolean regAsWalker);
    
    void logoutService(int mcgillID);

    void switchRoleService(int mcgillID);

}