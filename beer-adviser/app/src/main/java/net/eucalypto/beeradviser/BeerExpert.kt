package net.eucalypto.beeradviser

class BeerExpert {

    fun getBrands(color: String): List<String> {
        val brands = ArrayList<String>()

        if (color == "amber") {
            brands.add("Jack Amber")
            brands.add("Red Moose")
        } else {
            brands.add("Jail Pale Ale")
            brands.add("Gout Stout")
        }

        return brands
    }
}