package pl.konradgugala.ordertaxi.business.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


@Entity
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    @Size(min=4)
    private String orderDate;

    @NotNull
    @Size(min=4)
    private String make;

    @NotNull
    @Size(min=3)
    private String model;

    private String description;


    @ManyToOne
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Drive() {

    }

    public Drive(@NotNull @Size(min = 4) String make, @NotNull @Size(min = 3) String model, String description, City city) {
        this.make = make;
        this.model = model;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        this.orderDate = dateFormat.format(date);
        this.description = description;
        this.city = city;
    }
    public Drive(@NotNull @Size(min = 4) String make, @NotNull @Size(min = 3) String model, String description,@NotNull @Size(min=4)String orderDate,City city) {
        this.make = make;
        this.model = model;
        this.orderDate = orderDate;
        this.description = description;
        this.city = city;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getOrderDate() { return orderDate; }

    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        String string = "Make = [" + make +
                ",Model =" + model +
                ",Date = " + orderDate +
                ",Description = "+ description +
                ",City = "+ city.getTitle()+"]";
        return  string;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
