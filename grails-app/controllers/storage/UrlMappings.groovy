package storage

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'item')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
