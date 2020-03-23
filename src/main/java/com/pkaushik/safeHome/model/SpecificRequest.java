package com.pkaushik.safeHome.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pkaushik.safeHome.model.enumerations.RequestStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "request")
public class SpecificRequest {

    @Id
    @GeneratedValue
    @Column(name ="request_id")
    private Long request_id;

    @OneToOne(mappedBy = "request", fetch=FetchType.EAGER)
    @JsonBackReference
    Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="request_pickup_id",referencedColumnName = "location_id")
    Location pickupLocation;

    @Transient
    LocalDateTime pickupTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="request_destination_id",referencedColumnName = "location_id")
    Location destination;

    @Enumerated(EnumType.STRING)
    @Column(name="request_status")
    RequestStatus requestStatus;

    @Transient
    Assignment assignment; 

    SpecificRequest(){}
    
    public SpecificRequest(Student student, Location pickupLocation, Location destination){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = pickupLocation; 
        this.destination = destination;
    }
    
    public SpecificRequest(Student student, double pickupLatitude, double pickupLongitude,   
    double destinationLatitude, double destinationLongitude){
        this.requestStatus = RequestStatus.CREATED; 
        this.student = student; 
        this.pickupLocation = new Location(pickupLatitude, pickupLongitude); 
        this.destination = new Location(destinationLatitude, destinationLongitude);
    }
}
