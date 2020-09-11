package com.wujq.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer  {
	/**
	 * 不能忘记@bean
	 * @return
	 */
	@Bean
	public Docket createRestApi(){
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo()).groupName("Ums")
//				.select()
//				//为当前包下controller生成API文档
//				.apis(RequestHandlerSelectors.basePackage("com.wujq.springboot.controller"))
//				/*any() // 扫描所有，项目中的所有接口都会被扫描到
//				none() // 不扫描接口
//				withMethodAnnotation(final Class<? extends Annotation> annotation)// 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
//				withClassAnnotation(final Class<? extends Annotation> annotation) // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
//				basePackage(final String basePackage)*/
//				//为有@Api注解的Controller生成API文档
//				//.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//				//为有@ApiOperation注解的方法生成API文档
//				//.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//				// 错误路径不监控
////				.paths(PathSelectors.any())
//				.paths(Predicates.not(PathSelectors.regex("/error.*")))
//				.build();
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("wujq")
				.select()
				//RequestHandlerSelectors，配置需要扫描接口的方式
				//basePackage:指定扫描的包
				/*any() // 扫描所有，项目中的所有接口都会被扫描到
//				none() // 不扫描接口
//				withMethodAnnotation(final Class<? extends Annotation> annotation)// 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
//				withClassAnnotation(final Class<? extends Annotation> annotation) // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
//				basePackage(final String basePackage)*/
//				//为有@Api注解的Controller生成API文档
//				//.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//				//为有@ApiOperation注解的方法生成API文档
//				//.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//				// 错误路径不监控
				.apis(RequestHandlerSelectors.basePackage("com.wujq.springboot.controller"))
				.build();
	}

	private ApiInfo apiInfo() {
		//作者信息
		Contact contact = new Contact("名称","http://www.baidu.com","1219302084@qq.com");
		return new ApiInfo("API文档",
				"Swagger2的学习文档",
				"1.0",
				"http://www.baidu.com",
				contact, "Apache 2.0" +
				"" +
				"",
				"http://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList());
	}
}
