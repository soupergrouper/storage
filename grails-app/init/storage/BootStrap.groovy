package storage

class BootStrap {

    def init = { servletContext ->
        def kenzo = new Brand(name: "Kenzo")
        kenzo.save()
        def burberry = new Brand(name: "Burberry")
        burberry.save()
    }
    def destroy = {
    }
}
