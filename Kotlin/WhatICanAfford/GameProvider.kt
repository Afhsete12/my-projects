package game

class GameProvider {
    companion object {
        val gamesEneba = listOf(
            Game("(E)A Boy and His Blob", 3.63),
            Game("(E)Hidden Folks", 2.10),
            Game("(E)Cat Quest", 2.11),
            Game("(E)The Messenger", 3.77),
            Game("(E)Strider", 0.7),
            Game("(E)Rime & Shine", 2.22),
            Game("(E)Black Skylands", 1.25),
            Game("(E)Arietta of Spirits", 1.91),
            Game("(E)Ministry of Broadcast", 2.5),
            Game("(E)RIVE: Wreck, Hack, Die, Retry!", 0.85)
        )
        val gamesInstant = listOf(
            Game("(I)Death's Gambit: Afterlife", 5.05),
            Game("(I)Blasphemous ", 3.99)
        )
        val gameSteam = listOf(
            Game("(S)UDONGEIN X", 1.19),
            Game("(S)McPixel", 0.79),
            Game("(S)Darkestville Castle", 2.24),
            Game("(S)Catmaze", 2.69),
            Game("(S)La-Mulana", 3.74),
            Game("(E)Machinarium", 3.74),
            Game("(S)Moonlighter", 1.19),
            Game("(S)The Procession to Calvary", 4.04),
            Game("(S)Chasm", 4.19),
            Game("(S)Kaze and the Wild Masks", 4.99),
            Game("(S)Alwa's Legacy", 4.49),
            Game("(S)UnderMine", 5.87),
            Game("(S)Iconoclasts",5.99)
        )
        val gamesPSN = emptyList<Game>()
    }
}