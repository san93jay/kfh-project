package com.sanjay.kfh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanjay.kfh.dto.CourseDTO;
import com.sanjay.kfh.request.StudentLoginRequest;
import com.sanjay.kfh.security.KfhJwtToken;
import com.sanjay.kfh.serviceImpl.KfhStudentService;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Base64;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

	@ExtendWith(SpringExtension.class)
	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	@AutoConfigureMockMvc
	class KfhProjectApplicationTests {

		ObjectMapper objectMapper= new ObjectMapper();
		@Autowired
		private MockMvc mockMvc;
		static HttpHeaders authHeaders = new HttpHeaders();
		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private KfhJwtToken kfhJwtToken;

		@Autowired
		private KfhStudentService kfhStudentService;

		private static final String END_POINT_COURSE="/api/courses";

		@BeforeEach
		public void init() throws Exception {
				List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
				StudentLoginRequest studentLoginRequest=new StudentLoginRequest("sanju","Welcome@123");
			String token=mockMvc.perform(post("/api/student/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(studentLoginRequest))).andReturn().getResponse().getContentAsString();
			System.out.println(token + " Token");
			authHeaders.set("Authorization", "Bearer " + token);
		}

		@Test
		@Order(1)
		@DisplayName("status200WhenValidRequest")
		public void status200WhenValidRequest_Executed_thenSuccess() throws Exception {

			CourseDTO courseDTO=new CourseDTO(1l,"Data",200,"3 months","05-08-2023");
			String PostApiwithExistentRequest=END_POINT_COURSE+"/addCourse";
			mockMvc
					.perform(post(PostApiwithExistentRequest).headers(authHeaders).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDTO)))
					.andDo(print())
					.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		}

		@Test
		@Order(2)
		@DisplayName("statusCode404WhenNonExistentRequested")
		void statusCode404WhenNonExistentRequested() throws Exception {
			CourseDTO courseDTO=new CourseDTO(1l,"Data",200,"3 months","05-08-2023");
			String getApiwithNonExistentRequest=END_POINT_COURSE+"/-1";
			mockMvc
					.perform(get(getApiwithNonExistentRequest).headers(authHeaders).contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(courseDTO)))
					.andDo(print())
					.andExpect(status().isNotFound());
		}

		@Test
		@Order(3)
		@DisplayName("statusCode200WhenExistentRequested")
		void statusCode200whenExistentRequested() throws Exception {
			String getApiwithExistentRequest=END_POINT_COURSE+"/getAllCourses";
			CourseDTO courseDTO=new CourseDTO(1l,"Data",200,"3 months","05-08-2023");
			mockMvc
					.perform(get(getApiwithExistentRequest).headers(authHeaders)
							.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDTO)))
					.andDo(print())
					.andExpect(status().isOk());
		}

}
