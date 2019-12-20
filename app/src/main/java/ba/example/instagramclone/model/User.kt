package ba.example.instagramclone.model

data class User (
    var userId: String = "",
    var username: String = "",
    var email: String = "",
    var followers: Int = 0,
    var following: Int = 0,
    var posts: Int = 0,
    var bio: String = "",
    var imageUrl: String = ""
)