package sample;

import jssc.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import sample.Model.ComReader;


@Configuration
@PropertySource({"classpath:com.properties", "classpath:application.properties"})
@ComponentScan
public class SpringConfig {
    @Value("${serialPort.portName}")
    private String portName;

    @Bean
    public SerialPort serialPort(){
        return new SerialPort(portName);
    }

    @Bean
    public AnnotationMBeanExporter annotationMBeanExporter(){
        AnnotationMBeanExporter annotationMBeanExporter = new AnnotationMBeanExporter();
        annotationMBeanExporter.addExcludedBean("dataSource");
        return annotationMBeanExporter;
    }
}
