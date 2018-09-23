package storage


class Brand {

    String name;

    @Override
    String toString() {
        name
    }

    static constraints = {
        name nullable: false, blank: false
    }
}
