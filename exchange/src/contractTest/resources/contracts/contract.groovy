package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method GET()
        headers {
            contentType(applicationJson())
        }
        url ("/exchange")
        body(
                "sourceCurrency": "RUR",
                "targetCurrency": "USD",
                "amount": 80
        )
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                successful: true,
                amount: 1
        )
    }
}