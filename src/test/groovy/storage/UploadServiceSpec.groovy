package storage

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UploadService)
@Mock(Item)
class UploadServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test importing items from Excel"() {

        given:
            def kenzoHomme = new Item(externalId: 55556, name: 'Homme', brand: new Brand(name: "Kenzo"),
                    price: 25, quantity: 8, size: 30).save()
            def burberryBrit = new Item(externalId: 88866, name: 'Brit', brand: new Brand(name: "Burberry"),
                    price: 28, quantity: 4, size: 50).save()
            def is = this.class.classLoader.getResourceAsStream("items.xls")

        when: "excel import method called"
            service.importItemsExcel(is)

        then:
            Item.findByExternalId(55556).quantity == 13
            Item.findByExternalId(88866).quantity == 14
    }

    void "test importing items from csv file"() {

        given:
        def kenzoHomme = new Item(externalId: 55556, name: 'Homme', brand: new Brand(name: "Kenzo"),
                price: 25, quantity: 8, size: 30).save()
        def burberryBrit = new Item(externalId: 88866, name: 'Brit', brand: new Brand(name: "Burberry"),
                price: 28, quantity: 4, size: 50).save()
        def is = this.class.classLoader.getResourceAsStream("items.csv")

        when: "csv import method called"
        service.importItemsCsv(is)

        then:
        Item.findByExternalId(55556).quantity == 13
        Item.findByExternalId(88866).quantity == 14
    }
}
