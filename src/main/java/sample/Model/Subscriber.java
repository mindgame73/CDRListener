package sample.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="subscribers")
public class Subscriber implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence_sub", sequenceName = "sub_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_sub")
    private Integer sub_id;

    @Column(name="full_name")
    private String fullName;

    @Column(name="internal_num")
    private Integer internalNum;

    @Column(name="external_num")
    private Long externalNum;

    @Column
    private String building;

    @Column
    private String room;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="division_id", referencedColumnName = "division_id")
    private Division division;

    @Column(name="sub_descr")
    private String subDescr;

    @Column(name="gp_strip")
    private String gpStrip;

    @Column
    private String allocation;

    @Column(name="is_digital")
    private boolean isDigital;

    @Column
    private String cos;

    @Column(name="is_fax")
    private boolean isFax;

    @Column(name="is_sip")
    private boolean isSip;

    public Subscriber(String fullName, Integer internalNum, Long externalNum,
                      String building, String room, Division division,
                      String subDescr, String gpStrip, String allocation,
                      boolean isDigital, String cos, boolean isFax, boolean isSip) {
        this.fullName = fullName;
        this.internalNum = internalNum;
        this.externalNum = externalNum;
        this.building = building;
        this.room = room;
        this.division = division;
        this.subDescr = subDescr;
        this.gpStrip = gpStrip;
        this.allocation = allocation;
        this.isDigital = isDigital;
        this.cos = cos;
        this.isFax = isFax;
        this.isSip = isSip;
    }

    public Subscriber(){}

    public Subscriber(String fullName, int internalNum){
        this.fullName = fullName;
        this.internalNum = internalNum;
    }

    public Integer getSub_id() {
        return sub_id;
    }

    public void setSub_id(Integer sub_id) {
        this.sub_id = sub_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getInternalNum() {
        return internalNum;
    }

    public void setInternalNum(Integer internalNum) {
        this.internalNum = internalNum;
    }

    public Long getExternalNum() {
        return externalNum;
    }

    public void setExternalNum(Long externalNum) {
        this.externalNum = externalNum;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getSubDescr() {
        return subDescr;
    }

    public void setSubDescr(String subDescr) {
        this.subDescr = subDescr;
    }

    public String getGpStrip() {
        return gpStrip;
    }

    public void setGpStrip(String gpStrip) {
        this.gpStrip = gpStrip;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public boolean isDigital() {
        return isDigital;
    }

    public void setDigital(boolean digital) {
        isDigital = digital;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public boolean isFax() {
        return isFax;
    }

    public void setFax(boolean fax) {
        isFax = fax;
    }

    public boolean isSip() {
        return isSip;
    }

    public void setSip(boolean sip) {
        isSip = sip;
    }

    @Override
    public String toString() {
        try {
            String info = "";

        if (fullName != null) info = fullName;
        if (internalNum != null)
            if (internalNum != 0)
                info = info.concat(" " + internalNum.toString());
        return info;
        }
        catch (NullPointerException ex){
            System.out.println(fullName + " " + internalNum);
            return "Ошибка данных!";
        }
    }
}
