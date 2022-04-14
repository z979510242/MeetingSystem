package csu.software.meetingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "csu.software.meetingsystem.mapper")
public class MeetingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingSystemApplication.class, args);
    }

}
