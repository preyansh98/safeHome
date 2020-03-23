package com.pkaushik.safeHome.service;

import com.pkaushik.safeHome.model.SpecificRequest;

import java.util.List;

public interface RequestServiceIF{

    SpecificRequest createRequestService(int mcgillID, double pickupLatitude, double pickupLongitude,
                              double destinationLatitude, double destinationLongitude, int selectedWalkerId);

    List<SpecificRequest> listAllRequestsCreatedByStudentService(int mcgillID);

    List<SpecificRequest> listAllPastRequestsForWalkerService(int mcgillID);

    SpecificRequest getCurrentRequestService(int mcgillID);

    void updateRequestService(int mcgillID, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude);

    void cancelRequestService(int mcgillID);
}