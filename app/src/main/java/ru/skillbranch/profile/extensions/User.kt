package ru.skillbranch.profile.extensions

import ru.skillbranch.profile.models.User
import ru.skillbranch.profile.models.UserView
import ru.skillbranch.profile.utils.Utils

fun User.toUserView(): UserView
{
    val nickname = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = when
    {
        lastVisit == null -> "Еще ни разу не был"
        isOnline -> "online"
        else -> "Последний раз был ${lastVisit.humanizeDiff()}"
    }

    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickname,
        initials = initials,
        status = status
    )

}
