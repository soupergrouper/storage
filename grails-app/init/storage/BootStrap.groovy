package storage

import storage.security.Role
import storage.security.User
import storage.security.UserRole

class BootStrap {

    def springSecurityService

    def init = { servletContext ->

        def userRole = Role.findByAuthority('ROLE_READ_ONLY') ?: new Role(authority: 'ROLE_READ_ONLY').save(failOnError: true)
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

        def userAdmin = User.findByUsername("admin") ?: new User (username: "admin", password: springSecurityService.encodePassword("admin"), enabled: true).save()
        def userReadOnly = User.findByUsername("read-only") ?: new User (username: "read-only", password: springSecurityService.encodePassword("read-only"), enabled: true).save()

        if (!userAdmin.authorities.contains(adminRole)) {
            UserRole.create(userAdmin, adminRole, true)
        }
        if (!userReadOnly.authorities.contains(userRole)) {
            UserRole.create(userReadOnly, userRole, true)
        }

        def kenzo = new Brand(name: "Kenzo")
        kenzo.save()
        def dg = new Brand(name: "Dolce & Gabbana")
        dg.save()
        def boss = new Brand(name: "Boss")
        boss.save()
        def burberry = new Brand(name: "Burberry")
        burberry.save()
        new Item(externalId: 55556, name: 'Homme', brand: kenzo,
            price: 25, quantity: 8, size: 30).save()
        new Item(externalId: 88866, name: 'Brit', brand: burberry,
            price: 28, quantity: 4, size: 50).save()
        new Item(externalId: 10292, name: 'The One', brand: dg,
            price: 33, quantity: 10, size: 100).save()
        new Item(externalId: 13021, name: 'Hugo Boss', brand: boss,
                price: 20, quantity: 3, size: 50).save()
    }
    def destroy = {
    }
}
