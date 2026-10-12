package com.example.data

data class RewardItem(
    val id: String,
    val title: String,
    val emoji: String,
    val requiredStars: Int,
    val description: String
)

object RewardRepository {
    val rewards = listOf(
        RewardItem(
            id = "star_beginner",
            title = "Bintang Pembelajar",
            emoji = "⭐",
            requiredStars = 10,
            description = "Langkah pertama mengenal huruf!"
        ),
        RewardItem(
            id = "sweet_apple",
            title = "Stiker Apel Manis",
            emoji = "🍎",
            requiredStars = 25,
            description = "Hadiah manis untuk anak rajin!"
        ),
        RewardItem(
            id = "cat_friend",
            title = "Kucing Gemoy",
            emoji = "🐱",
            requiredStars = 50,
            description = "Meong! Kamu sudah mengenal banyak huruf!"
        ),
        RewardItem(
            id = "rainbow_magic",
            title = "Pelangi Ceria",
            emoji = "🌈",
            requiredStars = 80,
            description = "Warna-warni dunia hurufmu semakin luas!"
        ),
        RewardItem(
            id = "rocket_genius",
            title = "Roket Bintang",
            emoji = "🚀",
            requiredStars = 120,
            description = "Meluncur tinggi menjadi anak pintar!"
        ),
        RewardItem(
            id = "gentle_elephant",
            title = "Gajah Sahabat",
            emoji = "🐘",
            requiredStars = 170,
            description = "Kuat dan cerdas mengingat semua huruf!"
        ),
        RewardItem(
            id = "golden_trophy",
            title = "Piala Alfabet Emas",
            emoji = "🏆",
            requiredStars = 230,
            description = "Piala kehormatan juara belajar A-Z!"
        ),
        RewardItem(
            id = "royal_crown",
            title = "Mahkota Raja & Ratu Kata",
            emoji = "👑",
            requiredStars = 300,
            description = "Selamat! Kamu penguasa Dunia Huruf!"
        )
    )

    fun getLevel(stars: Int): LevelInfo {
        return when {
            stars < 30 -> LevelInfo(1, "Penjelajah Huruf Pemula", "🌱", 30)
            stars < 80 -> LevelInfo(2, "Sahabat Alfabet Cilik", "⭐", 80)
            stars < 150 -> LevelInfo(3, "Jagoan Kata Hebat", "🌟", 150)
            stars < 250 -> LevelInfo(4, "Pakar Membaca Pintar", "🎓", 250)
            else -> LevelInfo(5, "Bintang Cendekia Cilik", "👑", 500)
        }
    }
}

data class LevelInfo(
    val levelNumber: Int,
    val levelName: String,
    val badgeEmoji: String,
    val nextLevelStars: Int
)
