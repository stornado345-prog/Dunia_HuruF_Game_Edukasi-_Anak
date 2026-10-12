package com.example.data

import androidx.compose.ui.graphics.Color

data class AlphabetItem(
    val letter: Char,
    val uppercase: String,
    val lowercase: String,
    val word: String,
    val emoji: String,
    val category: String,
    val description: String,
    val sentence: String,
    val primaryColor: Color,
    val containerColor: Color
)

object AlphabetRepository {
    val items = listOf(
        AlphabetItem(
            letter = 'A',
            uppercase = "A",
            lowercase = "a",
            word = "Apel",
            emoji = "🍎",
            category = "Buah Manis",
            description = "Apel merah yang manis dan renyah.",
            sentence = "A untuk Apel! Nyam nyam segar!",
            primaryColor = Color(0xFFEF5350),
            containerColor = Color(0xFFFFEBEE)
        ),
        AlphabetItem(
            letter = 'B',
            uppercase = "B",
            lowercase = "b",
            word = "Bola",
            emoji = "⚽",
            category = "Mainan Olahraga",
            description = "Bola bulat untuk bermain bersama teman.",
            sentence = "B untuk Bola! Ayo oper bolanya!",
            primaryColor = Color(0xFF42A5F5),
            containerColor = Color(0xFFE3F2FD)
        ),
        AlphabetItem(
            letter = 'C',
            uppercase = "C",
            lowercase = "c",
            word = "Ceri",
            emoji = "🍒",
            category = "Buah Ceria",
            description = "Ceri merah kecil yang manis dan harum.",
            sentence = "C untuk Ceri! Merah dan imut!",
            primaryColor = Color(0xFFEC407A),
            containerColor = Color(0xFFFCE4EC)
        ),
        AlphabetItem(
            letter = 'D',
            uppercase = "D",
            lowercase = "d",
            word = "Domba",
            emoji = "🐑",
            category = "Hewan Ternak",
            description = "Domba lucu berbulu putih tebal dan lembut.",
            sentence = "D untuk Domba! Mbeee suara domba!",
            primaryColor = Color(0xFF7E57C2),
            containerColor = Color(0xFFEDE7F6)
        ),
        AlphabetItem(
            letter = 'E',
            uppercase = "E",
            lowercase = "e",
            word = "Elang",
            emoji = "🦅",
            category = "Burung Gagah",
            description = "Burung elang yang terbang tinggi di angkasa.",
            sentence = "E untuk Elang! Terbang meluncur bebas!",
            primaryColor = Color(0xFF8D6E63),
            containerColor = Color(0xFFEFEBE9)
        ),
        AlphabetItem(
            letter = 'F',
            uppercase = "F",
            lowercase = "f",
            word = "Flamingo",
            emoji = "🦩",
            category = "Burung Cantik",
            description = "Flamingo merah muda anggun berkaki panjang.",
            sentence = "F untuk Flamingo! Berdiri dengan satu kaki!",
            primaryColor = Color(0xFFFF4081),
            containerColor = Color(0xFFFCE4EC)
        ),
        AlphabetItem(
            letter = 'G',
            uppercase = "G",
            lowercase = "g",
            word = "Gajah",
            emoji = "🐘",
            category = "Hewan Pintar",
            description = "Gajah bertubuh besar dengan belalai panjang.",
            sentence = "G untuk Gajah! Hewan ramah yang kuat!",
            primaryColor = Color(0xFF78909C),
            containerColor = Color(0xFFECEFF1)
        ),
        AlphabetItem(
            letter = 'H',
            uppercase = "H",
            lowercase = "h",
            word = "Harimau",
            emoji = "🐯",
            category = "Hewan Hutan",
            description = "Harimau gagah bercorak belang jingga hitam.",
            sentence = "H untuk Harimau! Roarr gagah sekali!",
            primaryColor = Color(0xFFFF9800),
            containerColor = Color(0xFFFFF3E0)
        ),
        AlphabetItem(
            letter = 'I',
            uppercase = "I",
            lowercase = "i",
            word = "Ikan",
            emoji = "🐟",
            category = "Hewan Air",
            description = "Ikan cantik yang berenang lincah di danau.",
            sentence = "I untuk Ikan! Berenang kesana kemari!",
            primaryColor = Color(0xFF26A69A),
            containerColor = Color(0xFFE0F2F1)
        ),
        AlphabetItem(
            letter = 'J',
            uppercase = "J",
            lowercase = "j",
            word = "Jerapah",
            emoji = "🦒",
            category = "Hewan Tinggi",
            description = "Jerapah sahabat anak dengan leher yang sangat tinggi.",
            sentence = "J untuk Jerapah! Menggapai daun di pucuk pohon!",
            primaryColor = Color(0xFFFFA726),
            containerColor = Color(0xFFFFF8E1)
        ),
        AlphabetItem(
            letter = 'K',
            uppercase = "K",
            lowercase = "k",
            word = "Kucing",
            emoji = "🐱",
            category = "Sahabat Lucu",
            description = "Kucing imut yang suka mengusap dan bersuara meong.",
            sentence = "K untuk Kucing! Meong meong imut!",
            primaryColor = Color(0xFFAB47BC),
            containerColor = Color(0xFFF3E5F5)
        ),
        AlphabetItem(
            letter = 'L',
            uppercase = "L",
            lowercase = "l",
            word = "Lumba-lumba",
            emoji = "🐬",
            category = "Hewan Laut Pintar",
            description = "Lumba-lumba cerdas yang suka meloncat gembira.",
            sentence = "L untuk Lumba-lumba! Lompat tinggi di ombak biru!",
            primaryColor = Color(0xFF29B6F6),
            containerColor = Color(0xFFE1F5FE)
        ),
        AlphabetItem(
            letter = 'M',
            uppercase = "M",
            lowercase = "m",
            word = "Mobil",
            emoji = "🚗",
            category = "Kendaraan Cepat",
            description = "Mobil beroda empat untuk jalan-jalan bersama keluarga.",
            sentence = "M untuk Mobil! Brum brum berangkat!",
            primaryColor = Color(0xFFE53935),
            containerColor = Color(0xFFFFEBEE)
        ),
        AlphabetItem(
            letter = 'N',
            uppercase = "N",
            lowercase = "n",
            word = "Nanas",
            emoji = "🍍",
            category = "Buah Segar",
            description = "Nanas manis dengan mahkota daun hijau unik.",
            sentence = "N untuk Nanas! Rasa asam manis segar!",
            primaryColor = Color(0xFFFBC02D),
            containerColor = Color(0xFFFFFDE7)
        ),
        AlphabetItem(
            letter = 'O',
            uppercase = "O",
            lowercase = "o",
            word = "Orangutan",
            emoji = "🦧",
            category = "Sahabat Hutan",
            description = "Orangutan pandai bergelantungan di dahan pohon rimbun.",
            sentence = "O untuk Orangutan! Hewan lindungan Indonesia!",
            primaryColor = Color(0xFFA1887F),
            containerColor = Color(0xFFD7CCC8)
        ),
        AlphabetItem(
            letter = 'P',
            uppercase = "P",
            lowercase = "p",
            word = "Pisang",
            emoji = "🍌",
            category = "Buah Manis",
            description = "Pisang kuning lezat yang menyehatkan tubuh.",
            sentence = "P untuk Pisang! Kupas kulitnya, makan buahnya!",
            primaryColor = Color(0xFFFFCA28),
            containerColor = Color(0xFFFFF9C4)
        ),
        AlphabetItem(
            letter = 'Q',
            uppercase = "Q",
            lowercase = "q",
            word = "Quokka",
            emoji = "🦘",
            category = "Hewan Periang",
            description = "Quokka hewan mungil yang dikenal selalu tersenyum bahagia.",
            sentence = "Q untuk Quokka! Selalu riang dan ramah!",
            primaryColor = Color(0xFF8D6E63),
            containerColor = Color(0xFFEFEBE9)
        ),
        AlphabetItem(
            letter = 'R',
            uppercase = "R",
            lowercase = "r",
            word = "Rusa",
            emoji = "🦌",
            category = "Hewan Anggun",
            description = "Rusa lincah bertanduk indah dan pandai berlari cepat.",
            sentence = "R untuk Rusa! Berlari riang di padang rumput!",
            primaryColor = Color(0xFF6D4C41),
            containerColor = Color(0xFFEFEBE9)
        ),
        AlphabetItem(
            letter = 'S',
            uppercase = "S",
            lowercase = "s",
            word = "Sapi",
            emoji = "🐮",
            category = "Sahabat Peternakan",
            description = "Sapi penghasil susu lezat dan segar untuk anak hebat.",
            sentence = "S untuk Sapi! Mooo minum susu agar kuat!",
            primaryColor = Color(0xFF5C6BC0),
            containerColor = Color(0xFFE8EAF6)
        ),
        AlphabetItem(
            letter = 'T',
            uppercase = "T",
            lowercase = "t",
            word = "Tomat",
            emoji = "🍅",
            category = "Buah Berwarna",
            description = "Tomat merah segar kaya vitamin untuk kesehatan mata.",
            sentence = "T untuk Tomat! Bulat berkilau penuh vitamin!",
            primaryColor = Color(0xFFE53935),
            containerColor = Color(0xFFFFEBEE)
        ),
        AlphabetItem(
            letter = 'U',
            uppercase = "U",
            lowercase = "u",
            word = "Udang",
            emoji = "🦐",
            category = "Hewan Sungai & Laut",
            description = "Udang berenang mundur dan memiliki sungut panjang.",
            sentence = "U untuk Udang! Melompat gesit di air!",
            primaryColor = Color(0xFFFF7043),
            containerColor = Color(0xFFFBE9E7)
        ),
        AlphabetItem(
            letter = 'V',
            uppercase = "V",
            lowercase = "v",
            word = "Vas",
            emoji = "🏺",
            category = "Benda Cantik",
            description = "Vas tempat meletakkan bunga wangi yang mekar.",
            sentence = "V untuk Vas! Menghias ruangan jadi indah!",
            primaryColor = Color(0xFF26A69A),
            containerColor = Color(0xFFE0F2F1)
        ),
        AlphabetItem(
            letter = 'W',
            uppercase = "W",
            lowercase = "w",
            word = "Wortel",
            emoji = "🥕",
            category = "Sayur Sehat",
            description = "Wortel oranye manis kesukaan kelinci lucu.",
            sentence = "W untuk Wortel! Makan wortel bikin mata jernih!",
            primaryColor = Color(0xFFFF8A65),
            containerColor = Color(0xFFFBE9E7)
        ),
        AlphabetItem(
            letter = 'X',
            uppercase = "X",
            lowercase = "x",
            word = "Xilofon",
            emoji = "🎵",
            category = "Alat Musik Ceria",
            description = "Xilofon berbilah warna-warni penghasil bunyi ting-ting merdu.",
            sentence = "X untuk Xilofon! Ting ting tong mari bermusik!",
            primaryColor = Color(0xFF26C6DA),
            containerColor = Color(0xFFE0F7FA)
        ),
        AlphabetItem(
            letter = 'Y',
            uppercase = "Y",
            lowercase = "y",
            word = "Yo-yo",
            emoji = "🪀",
            category = "Mainan Klasik",
            description = "Yo-yo bertali yang bisa berputar naik dan turun lincah.",
            sentence = "Y untuk Yo-yo! Putar talinya dan tarik ke atas!",
            primaryColor = Color(0xFFFFA000),
            containerColor = Color(0xFFFFF8E1)
        ),
        AlphabetItem(
            letter = 'Z',
            uppercase = "Z",
            lowercase = "z",
            word = "Zebra",
            emoji = "🦓",
            category = "Hewan Unik",
            description = "Zebra bersahabat dengan garis belang hitam putih.",
            sentence = "Z untuk Zebra! Berlari kencang di alam luas!",
            primaryColor = Color(0xFF455A64),
            containerColor = Color(0xFFECEFF1)
        )
    )

    fun getItem(letter: Char): AlphabetItem {
        return items.firstOrNull { it.letter.equals(letter, ignoreCase = true) } ?: items[0]
    }
}
