package pl.konradgugala.ordertaxi.business.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 4)
    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "city")
    public Set<Drive> drives;

    public City() {
        drives = new HashSet<>();
    }

    public City(@NotNull @Size(min = 4) String title) {
        this();
        this.title = title;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Drive> getDrives() {
        return drives;
    }

    public void setDrives(Set<Drive> drives) {
        this.drives = drives;
    }

    @Override
    public String toString() {
        String string = "Name = " + title;
        for (Drive drive : drives) {
            string += "\nDrive - = " + drive.toString();
        }
        return string;
    }
}
