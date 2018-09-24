package storage


class Item {

    Long externalId
    String name
    Brand brand
    Integer size
    Integer price
    Integer quantity

    static constraints = {
        externalId nullable: false, unique: true
        name blank: false, nullable: false
        brand nullable: false
        price nullable: false, min: 0
        size nullable: false, min: 1
        quantity nullable: false, min: 0
    }

}