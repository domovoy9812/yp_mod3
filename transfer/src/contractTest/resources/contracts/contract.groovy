package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url("/transfer")
        body(
                "source": "user1",
                "sourceCurrency": "RUR",
                "target": "user2",
                "targetCurrency": "RUR",
                "sourceAmount": 50
        )
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body([
                successful: true,
                from   : [
                        currency: "RUR",
                        balance : 50
                ],
                to     : [
                        currency: "RUR",
                        balance : 50
                ]
        ]
        )
    }
}