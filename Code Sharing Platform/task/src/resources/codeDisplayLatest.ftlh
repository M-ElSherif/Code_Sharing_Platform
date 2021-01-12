<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>

    <style>
        #load_date {
            color: green;
        }

        .code_block {
            width: 75%;
            border-style: solid;
            border-width: 1px;
            background: lightgray;
        }

        #code_snippet {
            word-wrap: normal;
        }
    </style>
</head>
<body>

<code><span id="load_date">${code.date}</span></code>
<div class="code_block">
    <pre id="code_snippet"><code>${code.code}</code></pre>
</div>

</body>
</html>