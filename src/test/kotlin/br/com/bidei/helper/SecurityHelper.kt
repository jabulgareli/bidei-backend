package br.com.bidei.helper

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

open class SecurityHelper {

    @Autowired
    lateinit var mockMvc: MockMvc



    @BeforeEach
    fun setUpSecurity() {
//        val claimsSet = JWTClaimsSet.Builder()
//                .subject("3b7fdb46-478a-44b3-9ad1-caaee9c48f4c")
//                .expirationTime(Date(System.currentTimeMillis() * 1000L))
//                .claim("auth_time", System.currentTimeMillis() * 1000L)
//                .claim("iat", Date())
//                .claim("exp", Date())
//                .claim("cognito:username", username)
//                .claim("email", email)
//                .claim("phone_number", phoneNumber)
//                .build()
//        SecurityContextHolder.getContext().authentication = CognitoAuthenticationTokenDto("", claimsSet)
    }

}