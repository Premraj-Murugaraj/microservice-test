import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("contract definition for get car")
    name("should return a car")
    priority(1)
    request {
        method(GET())
        url("/api/cars")
    }
    response {
        status(200)
        status(OK())
        body(file("list_cars.json"))
    }
}
