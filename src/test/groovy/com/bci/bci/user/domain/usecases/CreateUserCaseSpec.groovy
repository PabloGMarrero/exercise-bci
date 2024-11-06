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

    def "create user when validations are ok"() {
        given:
        CreateUserRequest request = CreateUserRequest.builder().email("test").password("pass").build()
        String token = "token"
        User user = User.builder().id(UUID.randomUUID()).build()

        when:
        def response = useCase.create(request)

        then:
        1 * getUserProvider.getByEmail(request.email) >> null
        1 * passEncoder.encode(request.password) >> request.password
        //TODO ver como hacer para que sea un any request para que el mock devuelva el user
        1 * createUserProvider.createUser(_) >> user
        1 * authProvider.generateToken(user) >> token
        response.id != null
        response.token == token
    }
}
