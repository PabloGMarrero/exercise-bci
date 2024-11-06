package com.bci.bci.user.domain.usecases

import com.bci.bci.user.domain.models.User
import com.bci.bci.user.domain.ports.out.AuthenticationProvider
import com.bci.bci.user.domain.ports.out.GetUserProvider
import com.bci.bci.user.domain.ports.out.UpdateUserProvider
import com.bci.bci.user.infrastructure.adapters.in.rest.request.LoginUserRequest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class LoginUserUseCaseSpec extends Specification {

    private getUserProvider = Mock(GetUserProvider);
    private updateUserProvider = Mock(UpdateUserProvider);
    private authenticationProvider = Mock(AuthenticationProvider);
    private passwordEncoder = Mock(PasswordEncoder);
    private useCase = new LoginUserUseCase(getUserProvider, updateUserProvider, authenticationProvider, passwordEncoder)

    def "return user when validations are ok"() {
        given:
        LoginUserRequest request = LoginUserRequest.builder().email("test").build()
        String token = "token"
        User user = User.builder().id(UUID.randomUUID()).build()

        when:
        def response = useCase.login(request)

        then:
        1 * getUserProvider.getByEmail(request.email) >> user
        1 * updateUserProvider.update(user) >> user
        1 * authenticationProvider.generateToken(user) >> token
        response.id != null
        response.token == token
    }
}
