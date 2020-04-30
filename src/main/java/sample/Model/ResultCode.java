package sample.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="result_codes")
public class ResultCode implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence_rescode", sequenceName = "rescode_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_rescode")
    private Integer result_id;

    @Column(name="result_code",unique = true)
    private String resultCode;

    @Column(name="result_descr")
    private String resultDescr;

    public ResultCode(){

    }

    public ResultCode(String resultCode, String resultDescr) {
        this.resultCode = resultCode;
        this.resultDescr = resultDescr;
    }

    public Integer getResult_id() {
        return result_id;
    }

    public void setResult_id(Integer result_id) {
        this.result_id = result_id;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescr() {
        return resultDescr;
    }

    public void setResultDescr(String resultDescr) {
        this.resultDescr = resultDescr;
    }
}
