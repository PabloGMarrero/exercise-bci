package com.bci.bci.user.domain.usecases

import com.bci.bci.user.domain.exceptions.CreateUserException
import com.bci.bci.user.domain.models.User
import com.bci.bci.user.domain.ports.out.AuthenticationProvider
import com.bci.bci.user.domain.ports.out.CreateUserProvider
import com.bci.bci.user.domain.ports.out.GetUserProvider
import com.bci.bci.user.infrastructure.adapters.in.rest.request.CreateUserRequest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class CreateUserCaseSpec extends Specification {
    private getUserProvider = Mock(GetUserProvider)
    private createUserProvider = Mock(CreateUserProvider)
    private authProvider = Mock(AuthenticationProvider)
    private passEncoder = Mock(PasswordEncoder)

    private useCase = new CreateUserUseCase(createUserProvider, getUserProvider, authProvider, passEncoder)

    def "raise exception when user already exist"() {
        given:
        def request = CreateUserRequest.builder().email("test").build()
        when:
        getUserProvider.getByEmail(request.email) >> User.builder().id(UUID.randomUUID()).build()

        useCase.create(request)

        then:
        def ex = thrown CreateUserException
        ex.message == CreateUserException.ALREADY_EXIST
    }
}
