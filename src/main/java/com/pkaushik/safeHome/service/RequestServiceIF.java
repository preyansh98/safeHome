package com.pkaushik.safeHome.service; 

public interface RequestServiceIF{

    void createRequestService(int mcgillID, double pickupLatitude, double pickupLongitude,
                              double destinationLatitude, double destinationLongitude);

    void listAllRequestsCreatedByStudentService(int mcgillID);

    void listAllPastRequestsForWalkerService(int mcgillID);

    void getCurrentRequestService(int mcgillID);

    void updateRequestService(int mcgillID, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude);

    void cancelRequestService(int mcgillID);
}