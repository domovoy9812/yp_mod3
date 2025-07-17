package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url ("/notification")
        body(
                source: "accounts-application",
                email: "test@dom.com",
                subject: "subject",
                message: "email message"
        )
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                successful: true
        )
    }
}