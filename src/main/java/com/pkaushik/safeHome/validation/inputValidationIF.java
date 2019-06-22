package com.pkaushik.safeHome.validation;

import java.math.BigInteger;

public interface InputValidationIF {

    void validateMcgillID(int mcgillID);

    void validatePhoneNo(BigInteger phoneNo);

    void validatePickup(double pickupLatitude, double pickupLongitude);

    void validateDestination(double destinationLatitude, double destinationLongitude);

}