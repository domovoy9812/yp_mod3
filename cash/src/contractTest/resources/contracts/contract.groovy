package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url ("/cash/get-cash")
        body(
                "user": "user1",
                "currency": "USD",
                "amount": 80
        )
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body([
                successful: true,
                account: [
                        currency: "USD",
                        balance: 20
                ]
             ]
        )
    }
}