<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <link rel="stylesheet" href="./codeEdit.css">

    <style>
        textarea {

        }
    </style>

    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value
            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status == 200) {
                alert("Success!");
            }
        }
    </script>
</head>

<body>
<textarea id="code_snippet" rows="10" cols="100" placeholder="Paste your code snippet here"></textarea>
<br>
<br>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
</body>
</html>