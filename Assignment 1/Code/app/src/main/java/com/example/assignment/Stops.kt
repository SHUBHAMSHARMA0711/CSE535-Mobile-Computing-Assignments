package com.example.assignment

data class StopsStructure (
    val stopName: String,
    val stopDistance: Double,
    var reached: Boolean = false
)

val stops = listOf(
    StopsStructure("KASHMERE GATE", 0.0),
    StopsStructure("LAL QUILA", 1.5),
    StopsStructure("JAMA MASJID", 2.7),
    StopsStructure("DELHI GATE", 3.9),
    StopsStructure("ITO", 5.1),
    StopsStructure("MANDI HOUSE", 6.3),
    StopsStructure("JANPATH", 7.5),
    StopsStructure("CENTRAL SECRETARIAT", 8.7),
    StopsStructure("KHAN MARKET", 9.9),
    StopsStructure("JLN STADIUM", 10.5),
    StopsStructure("JANGPURA", 11.1)
)

val lazyStops = listOf(
    StopsStructure("RAJ BAGH", 0.0),
    StopsStructure("SHAHEED NAGAR", 1.5),
    StopsStructure("DILSHAD GARDEN", 2.7),
    StopsStructure("JHILMIL", 3.9),
    StopsStructure("MANSAROVAR PARK", 5.1),
    StopsStructure("SHAHDARA", 6.3),
    StopsStructure("SEELAMPUR", 7.5),
    StopsStructure("WELCOME", 8.7),
    StopsStructure("SHASTRI PARK", 9.9),
    StopsStructure("KASHMERE GATE", 10.5),
    StopsStructure("LAL QUILA", 11.1),
    StopsStructure("JAMA MASJID", 12.3),
    StopsStructure("DELHI GATE", 13.5),
    StopsStructure("ITO", 14.7),
    StopsStructure("MANDI HOUSE", 15.9),
    StopsStructure("JANPATH", 17.1),
    StopsStructure("CENTRAL SECRETARIAT", 18.3),
    StopsStructure("KHAN MARKET", 19.5),
    StopsStructure("JLN STADIUM", 20.1),
    StopsStructure("JANGPURA", 21.1),
    StopsStructure("LAJPAT NAGAR", 22.1),
    StopsStructure("MOOLCHAND", 23.1),
    StopsStructure("KAILASH COLONY", 24.1),
    StopsStructure("NEHRU PLACE", 25.1),
    StopsStructure("KALKAJI MANDIR", 26.1),
    StopsStructure("GOVINDPURI", 27.1),
    StopsStructure("HARKESH NAGAR OKHLA", 28.1)
)