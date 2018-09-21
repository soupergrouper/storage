package storage

class BootStrap {

    def init = { servletContext ->
        def kenzo = new Brand(name: "Kenzo")
        kenzo.save()
        def burberry = new Brand(name: "Burberry")
        burberry.save()
        new Item(externalId: 55556, name: 'Homme', brand: kenzo,
            price: 25, quantity: 8, size: 30).save()
        new Item(externalId: 88866, name: 'Brit', brand: burberry,
            price: 28, quantity: 4, size: 50).save()
    }
    def destroy = {
    }
}
