var comboboxCriterium = document.getElementById("criterium");
var comboboxCriteriumUpdate = document.getElementById("criterium_update");
var comboboxOperation = document.getElementById("operation")
var label = document.getElementById("get_label");
var label_update = document.getElementById("update_field");

function comboboxSelectCriterium() {
    label.innerHTML = comboboxCriterium.value + " : ";
}


function comboboxSelectUpdate() {
    label_update.innerHTML = comboboxCriteriumUpdate.value + " : ";
}



/* post (adaugare persoana) */
document.getElementById('postData').addEventListener('submit', postData);
function postData(event) {
    event.preventDefault();

    let id = document.getElementById("id").value;
    let last_name = document.getElementById("lastName").value;
    let first_name = document.getElementById("firstName").value;
    let telephone = document.getElementById("telephone").value;

    fetch("/person", {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: id,
                lastName: last_name,
                firstName: first_name,
                telephoneNumber: telephone
            })
        }).then(function(res){
            res.json()
            console.log(res);
            document.getElementById("post_status").innerHTML = "HTTP RESPONSE : " + res.status;
            })
        .then((data) => console.log(data))
        .catch((err) => console.log(err));
}

/* put si get (update persoana) */
document.getElementById('putData').addEventListener('submit', putData);
function putData(){
    event.preventDefault();
    var id = document.getElementById("put_id").value;
    var lastName;
    var firstName;
    var telephoneNumber;
    /* get (extrage persoana cu id introdus) */
    fetch("/person/" + id).then((resp) => resp.json())
    .then(function(data){
        lastName = data['lastName'];
        firstName = data['firstName'];
        telephoneNumber = data['telephoneNumber'];
        json = data;

        let select_field = document.getElementById("criterium_update").value;
        if(select_field === "ID"){
            id = document.getElementById("put_field").value;
        }
        else if(select_field === "Prenume"){
            firstName = document.getElementById("put_field").value;
        }
        else if(select_field == "Nume"){
            lastName = document.getElementById("put_field").value;
        }
        else if(select_field == "Numar"){
            telephoneNumber = document.getElementById("put_field").value;
        }

        fetch("/person/" +  id, {
            method: 'put',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                 id: id,
                 lastName: lastName,
                 firstName: firstName,
                 telephoneNumber: telephoneNumber})
            }).then(function(res){
               res.json()
               console.log(res);
               document.getElementById("update_status").innerHTML = "HTTP RESPONSE : " + res.status;
            })
            .then((data) => console.log(data))
            .catch((err) => console.log(err));
    });
}

/* delete (stergerea unei persoane) */
document.getElementById('deleteData').addEventListener('submit', deleteData)
function deleteData(event){
    event.preventDefault();
    let id = document.getElementById("delete_input").value;

    fetch("/person/" + id, {
        method: 'delete',
    }).then(function(res){
          res.json()
          console.log(res);
          document.getElementById("delete_status").innerHTML = "HTTP RESPONSE : " + res.status;
    })
    .then((data) => console.log(data))
    .catch((err) => console.log(err));


}