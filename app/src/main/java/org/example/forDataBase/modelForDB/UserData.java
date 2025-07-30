package org.example.forDataBase.modelForDB;

import jakarta.persistence.*;

@Entity
@Table(name="userData")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "CountOctahedrons")
    private int userDataCountOctahedrons;

    @Column(name = "DataSizeMin")
    private double userDataSizeMin;

    @Column(name = "DataSizeMax")
    private double userDataSizeMax;

    @Column(name = "DataTotalSquare")
    private double userDataTotalSquare;
    
    public UserData() { }

    public UserData(User user, int countOctahedrons, double sizeMin, double sizeMax, double totalSquare) {
        this.user = user;
        this.userDataCountOctahedrons = countOctahedrons;
        this.userDataSizeMin = sizeMin;
        this.userDataSizeMax = sizeMax;
        this.userDataTotalSquare = totalSquare;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getUserDataCountOctahedrons() { return userDataCountOctahedrons; }
    public void setUserDataCountOctahedrons(int countOctahedrons) { this.userDataCountOctahedrons = countOctahedrons; }

    public double getUserDataSizeMin() { return userDataSizeMin; }
    public void setUserDataSizeMin(double sizeMin) { this.userDataSizeMin = sizeMin; }

    public double getUserDataSizeMax() { return userDataSizeMax; }
    public void setUserDataSizeMax(double sizeMax) { this.userDataSizeMax = sizeMax; }

    public double getUserDataTotalSquare() { return userDataTotalSquare; }
    public void setUserDataTotalSquare(double totalSquare) { this.userDataTotalSquare = totalSquare; }
}
