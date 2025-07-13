package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url ("/account-service/user")
        body(
                name: "user1",
                password: "12345",
                firstName: "first name",
                lastName: "last name",
                email: "test@dom.com"
        )
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                name: "user1",
                firstName: "first name",
                lastName: "last name",
                email: "test@dom.com"
        )
    }
}