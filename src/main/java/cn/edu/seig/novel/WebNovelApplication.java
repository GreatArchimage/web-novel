package cn.edu.seig.novel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.seig.novel.dao.mapper")
public class WebNovelApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebNovelApplication.class, args);
    }

}
