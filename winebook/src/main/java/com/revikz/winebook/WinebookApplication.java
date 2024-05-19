package com.revikz.winebook;

import com.revikz.winebook.user.UserRegisterLoginView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WinebookApplication {

	public static void main(String[] args) {
		//SpringApplication.run(WinebookApplication.class, args);
		var ctx = new SpringApplicationBuilder(WinebookApplication.class).headless(false).run(args);

		ctx.getBean(UserRegisterLoginView.class);
	}

}
