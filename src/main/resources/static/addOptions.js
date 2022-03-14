function addOptions(numTest) {
    let div = document.createElement("div");
    //let count = document.getElementsByClassName("custom-control-label").length + 1;
    console.log(numTest);
    let id = document.getElementsByClassName(numTest + "input").length + 1;
    div.className = "custom-control custom-checkbox mt-2";
    div.id = numTest + "Id" + id;
    div.innerHTML = "<input type=\"checkbox\" class=\"custom-control-input" + " " + numTest + "input\" id=" + numTest + "customCheck" + id + " name=\"" + numTest + "check\" value=" + (id-1) + ">\n" +
        "        <label class=\"custom-control-label" + " " + numTest + "opt\" id=" + numTest + "label" + id + " for=" + numTest + "customCheck" + id + ">\n" +
        "        <input type=\"text\" class=\"form-control\" id=" + numTest + "options" + id + " placeholder=" + id  + ")\ name=\"" + numTest + "options\">\n" +
        "        </label> " +
       // "        <button onclick=\"removeOptions(" + numTest + "," + id + ")\" class=\"btn btn-primary " + numTest + "butt\">Удалить вариант</button>" +
        "<a href=\"#\" style=\"color: black\"  data-bs-toggle=\"tooltip\" data-bs-placement=\"right\" title=\"Удалить вариант\">\n" +
        "                    <svg onclick=\"removeOptions(" + numTest + "," + id + ")\"  xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-archive-fill ml-2 1butt\" viewBox=\"0 0 16 16\">\n" +
        "                        <path d=\"M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z\"/>\n" +
        "                    </svg>\n" +
        "                </a>"

    let opt = document.getElementById(numTest + "optionstest");
    opt.append(div);

    /*<div class="custom-control custom-checkbox mt-2" id="${oldQuiz_index+1}Id${opt_index+1}">
        <input type="checkbox" class="custom-control-input ${oldQuiz_index+1}input" id="${oldQuiz_index+1}customCheck${opt_index+1}" name="${oldQuiz_index+1}check"  value="${opt_index}" <#list oldQuiz.answer as ans> <#if ans == opt_index>checked</#if> </#list> >
    <label class="custom-control-label ${oldQuiz_index+1}opt" id="${oldQuiz_index+1}label${opt_index+1}" for="${oldQuiz_index+1}customCheck${opt_index+1}">
        <input type="text" class="form-control" value="${opt}" id="${oldQuiz_index+1}options${opt_index+1}" name="${oldQuiz_index+1}options">
    </label>
    <button onclick="removeOptions(${oldQuiz_index+1},${opt_index + 1})" class="btn btn-primary mt-3 ${oldQuiz_index+1}butt">Удалить вариант</button>
</div>*/
}

function removeOptions(numTest, id) {
    let name = numTest + "Id" + id;
    //console.log(name);
    //let name = id + "Id";
    let div = document.getElementById(name);
    //console.log(div.id)
    //let check = document.getElementsByClassName("custom-control-label");
    let check = document.getElementsByClassName(numTest + "opt");
    div.remove();
    let check2 = document.getElementsByClassName(numTest + "opt");
    let values = document.getElementsByClassName(numTest + "input");
    let check3 = document.getElementsByName(numTest + "options");
    let button = document.getElementsByClassName(numTest + "butt");

    //console.log(button.length);
    let sum;
    for (i = 0; i < check2.length; i++) {
        check3.item(i).id = numTest + "options" + (i + 1);
        sum = i + 1;
        button.item(i).setAttribute('onclick',"removeOptions(" + numTest +"," + sum + ")");
        values.item(i).value = i;

    }

    while (id <= check.length) {
        let next = id + 1;
        let text = document.getElementById(numTest + "options" + id);
        let optionsID = document.getElementById( numTest + "Id" + next)
        let customCheck = document.getElementById(numTest + "customCheck" + next);
        let label = document.getElementById(numTest + "label" + next);
        //console.log(numTest + "Id" + next);

        optionsID.id = numTest + "Id" + id;



        text.placeholder = id + ")";
        customCheck.id = numTest + "customCheck" + id;
        label.id = numTest + "label" + id;
        //label.for = numTest + "customCheck" + id;
        label.setAttribute('for', numTest + "customCheck" + id);
        id++;
    }
}