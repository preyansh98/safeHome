package com.pkaushik.safeHome.model; 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pojo")
public class pojo implements Serializable {

    private static final long serialVersionUID = -946513104525345369L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 1; 

    @Column(name = "pk")
    private int pk; 

    @Column(name = "whatever")
    private String whatever; 

    public pojo(int pk, String whatever){
        this.pk = pk; 
        this.whatever = whatever; 
        this.id = this.id + 1; 
    }

    @Override
    public String toString(){
        return "ID " + id + " WHATEVER: " + whatever; 
    }

}