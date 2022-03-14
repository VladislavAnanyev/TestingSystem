function pageOne(page) {
    let pageOne = document.getElementById("first");
    console.log(pageOne.title);
    let url = new URL(document.location.href);
    url.searchParams.set("page",page)
    document.location.href = url;
}

function pageTwo(page) {
    let pageTwo = document.getElementById("second");
    console.log(pageTwo.title);
    let url = new URL(document.location.href);
    url.searchParams.set("page",page)
    document.location.href = url;
}

function pageThree(page) {
    let pageThree = document.getElementById("third");
    let url = new URL(document.location.href);
    url.searchParams.set("page",page)
    document.location.href = url;
}

function pageNext() {
    let url = new URL(document.location.href);
    url.searchParams.set("page",Number(url.searchParams.get("page")) + 1)
    document.location.href = url;
}

function pagePrev() {
    let url = new URL(document.location.href);
    if (Number(url.searchParams.get("page")) !== 0) {
        url.searchParams.set("page",Number(url.searchParams.get("page")) - 1)
    }

    document.location.href = url;
}