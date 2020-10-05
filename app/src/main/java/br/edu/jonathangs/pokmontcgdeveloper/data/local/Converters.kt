package br.edu.jonathangs.pokmontcgdeveloper.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toStringList(value: String): List<String> = value.split(regex = ",".toRegex())

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString()

}