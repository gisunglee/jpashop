package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	/**
	 * LAZY 로딩이면 잭슨아 너 아무것도 하지마!
	 * 근데 근데 아싸리 Entity 를 Json 만들지 않으면 이런일이 필요 없겠지
	 * */
	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}

}
