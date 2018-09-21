package storage

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Item)
class ItemSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test externalId not null"() {
        when: "externalId is null"
        domain.externalId = null

        then:
        !domain.validate(['externalId'])
        domain.errors['externalId'].code == 'nullable'
    }

    void "test name is not null"() {
        when: "name is null"
        domain.name = null

        then:
        !domain.validate(['name'])
        domain.errors['name'].code == 'nullable'
    }

    void "test name is not blank"() {
        when: "name is blank"
        domain.name = ""

        then:
        !domain.validate(['name'])
        domain.errors['name'].code == 'blank'
    }

    void "test brand is not null"() {
        when: "brand is null"
        domain.brand= null

        then:
        !domain.validate(['brand'])
        domain.errors['brand'].code == 'nullable'
    }

    void "test price is not null"() {
        when: "price is null"
        domain.price= null

        then:
        !domain.validate(['price'])
        domain.errors['price'].code == 'nullable'
    }

    void "test price is equals or greater than 0"() {
        when: "price is less than 0"
        domain.price = -1

        then:
        !domain.validate(['price'])
    }

    void "test size is not null"() {
        when: "size is null"
        domain.size= null

        then:
        !domain.validate(['size'])
        domain.errors['size'].code == 'nullable'
    }

    void "test size is greater than 0"() {
        when: "size is less than 0"
        domain.size = -1

        then:
        !domain.validate(['size'])
    }

    void "test quantity is not null"() {
        when: "quantity is null"
        domain.quantity= null

        then:
        !domain.validate(['quantity'])
        domain.errors['quantity'].code == 'nullable'
    }

    void "test quantity is equals or greater than 0"() {
        when: "quantity is less than 0"
        domain.quantity = -1

        then:
        !domain.validate(['quantity'])
    }

}
