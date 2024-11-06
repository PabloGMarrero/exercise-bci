package com.bci.bci.user.infrastructure.adapters.in.rest

import com.bci.bci.handler.GlobalExceptionHandler
import com.bci.bci.user.domain.ports.in.CreateUserPort
import com.bci.bci.user.domain.ports.in.LoginUserPort
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest
import com.bci.bci.user.infrastructure.adapters.in.rest.request.LoginUserRequest
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserCreatedResponse
import com.bci.bci.user.infrastructure.adapters.in.rest.response.UserLoginResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class SignupControllerSpec extends Specification {
    def createUserPort = Mock(CreateUserPort)
    def loginUserPort = Mock(LoginUserPort)
    def controller = new SignupController(createUserPort, loginUserPort)
    MockMvc mockMvc = standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build()

    def "sign up and create user"() {
        given:
        CreateUserRequest request = CreateUserRequest.builder().email("pablo@test.com").password("pass").build()

        when:
        def stringResponse = mockMvc.perform(post("/sign-up")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response.contentAsString

        def response = new ObjectMapper().readValue(stringResponse, UserCreatedResponse.class)

        then:
        1 * createUserPort.create(_) >> UserCreatedResponse.builder().id(UUID.randomUUID()).build()
        stringResponse != null
        response != null
        response.id != null

    }

    def "login user"() {
        given:
        LoginUserRequest request = LoginUserRequest.builder().email("pablo@test.com").password("pass").build()

        when:
        def stringResponse = mockMvc.perform(post("/login")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response.contentAsString

        def response = new ObjectMapper().readValue(stringResponse, UserLoginResponse.class)

        then:
        1 * loginUserPort.login(_) >> UserLoginResponse.builder().id(UUID.randomUUID()).build()
        stringResponse != null
        response != null
        response.id != null

    }

    String asJsonString(final Object obj) {
        try {
            ObjectMapper om = new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return om.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
