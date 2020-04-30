package sample.Model;


import javax.persistence.*;

@Entity(name="call_detail_records")
public class CallDetailRecord {

    private static int counter = -1;

    private int id;

    @Id
    @SequenceGenerator(name = "sequence_cdr", sequenceName = "cdr_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_cdr")
    private Integer call_id;

    @Column
    private int board_cdr_id;

    @Column(name="start_time", columnDefinition = "Timestamp")
    private String startTime;

    @Column(name="stop_time", columnDefinition = "Timestamp")
    private String stopTime;

    @Column(name="full_duration")
    private int fullTime;

    @Column(name="voice_duration")
    private int voiceTime;

    @Column(name="number_b")
    private long numberB;

    @Column(name="number_a")
    private long numberA;

    @Column
    private int mount_a;

    @Column
    private int mount_b;

    @Column(name="result_code")
    private String resultCode;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="result_id", referencedColumnName = "result_id")
    private ResultCode resultCodeObj;

    @Column(name="routing_table")
    private int routingTable;

    @Column(name="flow_port")
    private int flowPort;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="subB_id", referencedColumnName = "sub_id")
    private Subscriber subscriberB;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="subA_id", referencedColumnName = "sub_id")
    private Subscriber subscriberA;

    public CallDetailRecord(){
        counter++;
        this.id = counter;
    }

    public int getId(){
        return id;
    }

    public static class Builder {
        private CallDetailRecord newCallDetailRecord;

        public Builder(){
            newCallDetailRecord = new CallDetailRecord();
        }

        public Builder withCallId(int callId){
            newCallDetailRecord.call_id = callId;
            return this;
        }

        public Builder withBoardCdrId(int boardCdrId){
            newCallDetailRecord.board_cdr_id = boardCdrId;
            return this;
        }

        public Builder withStartTime(String startTime){
            newCallDetailRecord.startTime = startTime;
            return this;
        }

        public Builder withStopTime(String stopTime){
            newCallDetailRecord.stopTime = stopTime;
            return this;
        }

        public Builder withFullTime(int fullTime){
            newCallDetailRecord.fullTime = fullTime;
            return this;
        }

        public Builder withVoiceTime(int voiceTime){
            newCallDetailRecord.voiceTime = voiceTime;
            return this;
        }

        public Builder withNumberB(long numberB){
            newCallDetailRecord.numberB = numberB;
            return this;
        }

        public Builder withNumberA(long numberA){
            newCallDetailRecord.numberA = numberA;
            return this;
        }

        public Builder withMountA(int mountA){
            newCallDetailRecord.mount_a = mountA;
            return this;
        }

        public Builder withMountB(int mountB){
            newCallDetailRecord.mount_b = mountB;
            return this;
        }

        public Builder withResultCode(String resultCode){
            newCallDetailRecord.resultCode = resultCode;
            return this;
        }

        public Builder withRoutingTable(int routingTable){
            newCallDetailRecord.routingTable = routingTable;
            return this;
        }

        public Builder withFlowPort(int flowPort){
            newCallDetailRecord.flowPort = flowPort;
            return this;
        }

        public Builder withSubscriberB(Subscriber subscriberB){
            newCallDetailRecord.subscriberB = subscriberB;
            return this;
        }

        public Builder withSubscriberA(Subscriber subscriberA){
            newCallDetailRecord.subscriberA = subscriberA;
            return this;
        }

        public Builder withResultCodeObj(ResultCode resultCodeObj){
            newCallDetailRecord.resultCodeObj = resultCodeObj;
            return this;
        }

        public CallDetailRecord build(){
            return newCallDetailRecord;
        }

    }

    public Integer getCall_id() {
        return call_id;
    }

    public int getBoard_cdr_id() {
        return board_cdr_id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public int getFullTime() {
        return fullTime;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public long getNumberB() {
        return numberB;
    }

    public long getNumberA() {
        return numberA;
    }

    public int getMount_a() {
        return mount_a;
    }

    public int getMount_b() {
        return mount_b;
    }

    public String getResultCode() {
        return resultCode;
    }

    public ResultCode getResultCodeObj() {
        return resultCodeObj;
    }

    public int getRoutingTable() {
        return routingTable;
    }

    public int getFlowPort() {
        return flowPort;
    }

    public Subscriber getSubscriberB() {
        return subscriberB;
    }

    public Subscriber getSubscriberA() {
        return subscriberA;
    }

}
