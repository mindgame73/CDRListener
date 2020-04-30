package sample.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name="divisions")
public class Division implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence_div", sequenceName = "div_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_div")
    private Integer division_id;

    @Column(name="division_name",unique = true)
    private String divisionName;

    public Division(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getDivision_id() {
        return division_id;
    }

    public void setDivision_id(Integer division_id) {
        this.division_id = division_id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Division() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return division_id.equals(division.division_id) &&
                divisionName.equals(division.divisionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(division_id, divisionName);
    }
}
