package storage

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Brand)
class BrandSpec extends Specification {

    def setup() {
    }

    def cleanup() {
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
}
