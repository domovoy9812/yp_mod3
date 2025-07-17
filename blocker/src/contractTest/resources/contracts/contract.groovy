package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url ("/blocker/validate")
        body(
                operation: "TRANSFER",
                "source": "source_user",
                "target": "target_user",
                "amount": 100
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