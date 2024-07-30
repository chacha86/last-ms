package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String state;
    private String city;
    private String village;
    private int gridX;
    private int gridY;

    public void setData(int index, String data) {
        switch (index) {
            case 2:
                this.state = data;
                break;
            case 3:
                this.city = data;
                break;
            case 4:
                this.village = data;
                break;
            case 5:
                this.gridX = Integer.parseInt(data);
                break;
            case 6:
                this.gridY = Integer.parseInt(data);
                break;
            default:
                break;
        }
    }
}
