package zzuli;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: Application
 * Package: com.zzuli
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/8
 */
@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Transactional
public class Application {
    public static void main(String[] args) {
        log.info("server started");
        SpringApplication.run(Application.class, args);
    }
}