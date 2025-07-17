package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "описание"
    request {
        method GET()
        headers {
            contentType(applicationJson())
        }
        url ("/template/say-hello")
        /*body(
                param: "value"
        )*/
    }
    response {
        status 200
        /*headers {
            contentType(applicationJson())
        }
        body(
                param: "value"
        )*/
    }
}