function activeChat(username) {

    if (username.id) {
        location.href='/chat/' + username.id;
    } else {
        location.href='/chat/' + username
    }

    console.log(username)
    console.log(username.id)

    //document.location.replace('catalog.html')

}