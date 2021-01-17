<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>

    <style>
        textarea {

        }
    </style>

    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value,
                "time": document.getElementById("time_restriction").value,
                "views": document.getElementById("views_restriction").value
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
<label>Time restriction: </label>
<input id="time_restriction" type="text" />
<br>
<br>
<label>Views restriction: </label>
<input id="views_restriction" type="text" />
<br>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
<br>
<br>
</body>
</html>