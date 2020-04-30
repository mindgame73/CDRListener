package sample.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import sample.Repository.CallDetailRecordRepository;
import sample.Repository.ResultCodeRepository;
import sample.Repository.SubscriberRepository;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;

@Service
public class EventListener implements SerialPortEventListener {

    private static final Logger logger = Logger.getLogger(EventListener.class.getName());

    @Value("${logger.outputFilePath}")
    private String outputLoggerFile;

    private SerialPort serialPort;

    private CallDetailRecordRepository callDetailRecordRepository;

    private SubscriberRepository subscriberRepository;

    private ResultCodeRepository resultCodeRepository;

    private Controller controller;

    @Autowired
    public void setController(Controller controller){
        this.controller = controller;
    }

    @Autowired
    public void setSerialPort(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    @Autowired
    public void setCallDetailRecordRepository(CallDetailRecordRepository callDetailRecordRepository) {
        this.callDetailRecordRepository = callDetailRecordRepository;
    }

    @Autowired
    public void setSubscriberRepository(SubscriberRepository subscriberRepository){
        this.subscriberRepository = subscriberRepository;
    }

    @Autowired
    public void setResultCodeRepository(ResultCodeRepository resultCodeRepository) {
        this.resultCodeRepository = resultCodeRepository;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        String call = "";

        if(event.isRXCHAR()){
            try {
                call = serialPort.readString(event.getEventValue());
                System.out.println(call);
                parseAndPersist(call);
            } catch (SerialPortException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            } catch (IncorrectResultSizeDataAccessException e) {
                logger.log(Level.SEVERE, e.getMessage() + "\nEXCEPTION WAS CAUSED BY THIS STRING - " + call, e);
            }
            catch (NumberFormatException e){
                logger.log(Level.SEVERE, e.getMessage() + "\nEXCEPTION WAS CAUSED BY THIS STRING - " + call, e);
            }
            catch (Exception e){
                logger.log(Level.SEVERE, e.getMessage() + "\nNOT CAUGHT EXCEPTION", e);
            }
        }
    }

    private void parseAndPersist(String str) throws NumberFormatException, IncorrectResultSizeDataAccessException {
        String[] parsedString = str.split(";");

        CallDetailRecord.Builder builder = new CallDetailRecord.Builder()
                .withBoardCdrId(Integer.parseInt(parsedString[0]))
                .withStartTime(parsedString[1] + " " + parsedString[2])
                .withFullTime(Integer.parseInt(parsedString[4]))
                .withVoiceTime(Integer.parseInt(parsedString[5]))
                .withMountA(Integer.parseInt(parsedString[8]))
                .withMountB(Integer.parseInt(parsedString[9]))
                .withResultCode(parsedString[10])
                .withRoutingTable(Integer.parseInt(parsedString[11]))
                .withFlowPort(Integer.parseInt(parsedString[12].trim()));

                if (!parsedString[3].isEmpty()) builder.withStopTime(parsedString[1] + " " + parsedString[3]);

                if (!parsedString[6].isEmpty()) {
                    builder.withNumberB(Long.parseLong(parsedString[6]));
                    boolean subFound = subscriberRepository.findByExternalNum(Long.parseLong(84235 + parsedString[6])).isPresent();
                    if (subFound) builder.withSubscriberB(subscriberRepository.findByExternalNum(Long.parseLong(84235 + parsedString[6])).get());
                }

                if (!parsedString[7].isEmpty()) {
                    builder.withNumberA(Long.parseLong(parsedString[7]));
                    boolean subFound = subscriberRepository.findByExternalNum(Long.parseLong(parsedString[7])).isPresent();
                    if (subFound) builder.withSubscriberA(subscriberRepository.findByExternalNum(Long.parseLong(parsedString[7])).get());
                }

                boolean rsFound = resultCodeRepository.getResultCodeByResultCode(parsedString[10]).isPresent();
                if(rsFound) builder.withResultCodeObj(resultCodeRepository.getResultCodeByResultCode(parsedString[10]).get());

                CallDetailRecord cdr = builder.build();
                controller.addCdr(cdr);
                //callDetailRecordRepository.save(cdr);
    }

    @PostConstruct
    private void initLogger(){

        try {
            FileHandler fileHandler = new FileHandler(outputLoggerFile, true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getLoggerName() + ":" +record.getThreadID() + "\n" + new Date() + "\n" + record.getMessage() +
                            "\n" + Arrays.toString(record.getThrown().getStackTrace()) + "\n\n\n";
                }
            });
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
