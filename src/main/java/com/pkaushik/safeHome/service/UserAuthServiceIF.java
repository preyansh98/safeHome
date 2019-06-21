package com.pkaushik.safeHome.service;

import java.math.BigInteger;

public interface UserAuthServiceIF {

    public void loginService(int mcgillID, boolean loginAsWalker);

    public void registerService(BigInteger phoneNo, int mcgillID, boolean regAsWalker);
    
    public void logoutService(int mcgillID);

}