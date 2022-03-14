
function paging() {
    let pageSize = document.getElementById("inlineFormCustomSelect");

    let url = new URL(document.location.href);


    //if (pageSize.value !== "no") {
        url.searchParams.set("pageSize", pageSize.value);
        document.location.href = url;
    //}


}

