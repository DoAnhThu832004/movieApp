package com.example.movieapp.model.Response

data class PersonDetail(
    val adult: Boolean,
    val also_known_as: List<String>?, // Thêm ? vì có thể không có tên khác
    val biography: String?,           // Thêm ? vì tiểu sử có thể trống
    val birthday: String?,            // Thêm ? vì có thể thiếu ngày sinh
    val deathday: String?,            // Đổi từ Any? thành String?
    val gender: Int,
    val homepage: String?,            // Đổi từ Any? thành String?
    val id: Int,
    val imdb_id: String?,             // Thêm ? để an toàn
    val known_for_department: String?,
    val name: String,
    val place_of_birth: String?,      // Thêm ? vì có thể không có nơi sinh
    val popularity: Double,
    val profile_path: String?         // Thêm ? vì có thể không có ảnh đại diện
)
